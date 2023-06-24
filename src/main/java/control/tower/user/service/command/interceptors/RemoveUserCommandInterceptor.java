package control.tower.user.service.command.interceptors;

import control.tower.core.commands.RemoveAddressCommand;
import control.tower.core.commands.RemovePaymentMethodCommand;
import control.tower.core.query.queries.FindAllAddressesForUserQuery;
import control.tower.core.query.queries.FindAllPaymentMethodsForUserQuery;
import control.tower.core.query.querymodels.AddressQueryModel;
import control.tower.core.query.querymodels.PaymentMethodQueryModel;
import control.tower.user.service.command.commands.RemoveUserCommand;
import control.tower.user.service.core.data.UserLookupEntity;
import control.tower.user.service.core.data.UserLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

import static control.tower.core.constants.LogMessages.INTERCEPTED_COMMAND;
import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesNotExist;
import static control.tower.user.service.core.constants.ExceptionMessages.USER_WITH_ID_DOES_NOT_EXIST;

@Component
public class RemoveUserCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveUserCommandInterceptor.class);

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private CommandGateway commandGateway;

    private final UserLookupRepository userLookupRepository;

    public RemoveUserCommandInterceptor(UserLookupRepository userLookupRepository) {
        this.userLookupRepository = userLookupRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {

            if (RemoveUserCommand.class.equals(command.getPayloadType())) {
                LOGGER.info(String.format(INTERCEPTED_COMMAND, command.getPayloadType()));

                RemoveUserCommand removeUserCommand = (RemoveUserCommand) command.getPayload();

                removeUserCommand.validate();

                String userId = removeUserCommand.getUserId();

                UserLookupEntity userLookupEntity = userLookupRepository.findByUserId(userId);

                throwExceptionIfEntityDoesNotExist(userLookupEntity,
                        String.format(USER_WITH_ID_DOES_NOT_EXIST, userId));

                removeAddressesForUser(userId);

                removePaymentMethodsForUser(userId);
            }

            return command;
        };
    }

    private void removeAddressesForUser(String userId) {
        LOGGER.info("Fetching findAllAddressesForUserQuery");

        List<AddressQueryModel> addressQueryModels = queryGateway.query(new FindAllAddressesForUserQuery(userId),
                ResponseTypes.multipleInstancesOf(AddressQueryModel.class)).join();

        for (AddressQueryModel addressQueryModel: addressQueryModels) {
            String addressId = addressQueryModel.getAddressId();

            LOGGER.info("Issuing remove address command for address: " + addressId);

            RemoveAddressCommand removeAddressCommand = RemoveAddressCommand.builder()
                    .addressId(addressId)
                    .build();

            commandGateway.send(removeAddressCommand, (commandMessage, commandResultMessage) -> {
                if (commandResultMessage.isExceptional()) {
                    LOGGER.error("Exception encountered when issuing remove address command for address id: " + addressId);
                    throw new IllegalStateException("Failed to remove address " + addressId + ", cancelling remove user command");
                }
            });
        }
    }

    private void removePaymentMethodsForUser(String userId) {
        LOGGER.info("Fetching findAllPaymentMethodsForUserQuery");

        List<PaymentMethodQueryModel> paymentMethodQueryModels = queryGateway.query(new FindAllPaymentMethodsForUserQuery(userId),
                ResponseTypes.multipleInstancesOf(PaymentMethodQueryModel.class)).join();

        for (PaymentMethodQueryModel paymentMethodQueryModel: paymentMethodQueryModels) {
            String paymentId = paymentMethodQueryModel.getPaymentId();

            LOGGER.info("Issuing remove payment method command for payment method: " + paymentId);

            RemovePaymentMethodCommand removePaymentMethodCommand = RemovePaymentMethodCommand.builder()
                    .paymentId(paymentId)
                    .build();

            commandGateway.send(removePaymentMethodCommand, (commandMessage, commandResultMessage) -> {
                if (commandResultMessage.isExceptional()) {
                    LOGGER.error("Exception encountered when issuing remove payment method command for payment method id: " + paymentId);
                    throw new IllegalStateException("Failed to remove payment method " + paymentId + ", cancelling remove user command");
                }
            });
        }
    }
}
