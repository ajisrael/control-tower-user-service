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
import static control.tower.user.service.core.constants.ExceptionMessages.*;
import static control.tower.user.service.core.constants.LogMessages.*;

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

                List<AddressQueryModel> addressesForUser = getAddressesForUser(userId);

                List<PaymentMethodQueryModel> paymentMethodsForUser = getPaymentMethodsForUser(userId);

                // TODO: If an exception is encountered in removing addresses or payment methods catch it and issue
                //       command to start retry saga for x number of times and raise notification event if unable
                //       to resolve automatically

                removeAddressesForUser(addressesForUser);

                removePaymentMethodsForUser(paymentMethodsForUser);
            }

            return command;
        };
    }

    private List<AddressQueryModel> getAddressesForUser(String userId) {
        LOGGER.info(FETCHING_FIND_ALL_ADDRESSES_FOR_USER_QUERY);

        FindAllAddressesForUserQuery findAllAddressesForUserQuery = FindAllAddressesForUserQuery.builder()
                .userId(userId)
                .build();

        return queryGateway.query(findAllAddressesForUserQuery,
                ResponseTypes.multipleInstancesOf(AddressQueryModel.class)).join();
    }

    private List<PaymentMethodQueryModel> getPaymentMethodsForUser(String userId) {
        LOGGER.info(FETCHING_FIND_ALL_PAYMENT_METHODS_FOR_USER_QUERY);

        FindAllPaymentMethodsForUserQuery findAllPaymentMethodsForUserQuery = FindAllPaymentMethodsForUserQuery.builder()
                .userId(userId)
                .build();

        return queryGateway.query(findAllPaymentMethodsForUserQuery,
                ResponseTypes.multipleInstancesOf(PaymentMethodQueryModel.class)).join();
    }

    private void removeAddressesForUser(List<AddressQueryModel> addressQueryModels) {
        for (AddressQueryModel addressQueryModel: addressQueryModels) {
            String addressId = addressQueryModel.getAddressId();

            LOGGER.info(String.format(ISSUING_REMOVE_ADDRESS_COMMAND_FOR_ADDRESS_WITH_ID, addressId));

            RemoveAddressCommand removeAddressCommand = RemoveAddressCommand.builder()
                    .addressId(addressId)
                    .build();

            commandGateway.send(removeAddressCommand, (commandMessage, commandResultMessage) -> {
                if (commandResultMessage.isExceptional()) {
                    LOGGER.error(String.format(EXCEPTION_ENCOUNTERED_WHEN_ISSUING_REMOVE_ADDRESS_COMMAND_FOR_ADDRESS_WITH_ID, addressId));
                    throw new IllegalStateException(String.format(FAILED_TO_REMOVE_ADDRESS_WITH_ID_CANCELLING_REMOVE_USER_COMMAND, addressId));
                }
            });
        }
    }

    private void removePaymentMethodsForUser(List<PaymentMethodQueryModel> paymentMethodQueryModels) {
        for (PaymentMethodQueryModel paymentMethodQueryModel: paymentMethodQueryModels) {
            String paymentId = paymentMethodQueryModel.getPaymentId();

            LOGGER.info(String.format(ISSUING_REMOVE_PAYMENT_METHOD_COMMAND_FOR_PAYMENT_METHOD_WITH_ID, paymentId));

            RemovePaymentMethodCommand removePaymentMethodCommand = RemovePaymentMethodCommand.builder()
                    .paymentId(paymentId)
                    .build();

            commandGateway.send(removePaymentMethodCommand, (commandMessage, commandResultMessage) -> {
                if (commandResultMessage.isExceptional()) {
                    LOGGER.error(String.format(EXCEPTION_ENCOUNTERED_WHEN_ISSUING_REMOVE_PAYMENT_METHOD_COMMAND_FOR_PAYMENT_METHOD_WITH_ID, paymentId));
                    throw new IllegalStateException(String.format(FAILED_TO_REMOVE_PAYMENT_METHOD_WITH_ID_CANCELLING_REMOVE_USER_COMMAND, paymentId));
                }
            });
        }
    }
}
