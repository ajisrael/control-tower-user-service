package control.tower.user.service.command.interceptors;

import control.tower.user.service.command.commands.CreateUserCommand;
import control.tower.user.service.core.data.UserLookupEntity;
import control.tower.user.service.core.data.UserLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

import static control.tower.core.constants.LogMessages.INTERCEPTED_COMMAND;
import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesExist;
import static control.tower.user.service.core.constants.ExceptionMessages.USER_WITH_ID_EMAIL_OR_PHONE_NUMBER_ALREADY_EXISTS;

@Component
public class CreateUserCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUserCommandInterceptor.class);

    private final UserLookupRepository userLookupRepository;

    public CreateUserCommandInterceptor(UserLookupRepository userLookupRepository) {
        this.userLookupRepository = userLookupRepository;
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {

            if (CreateUserCommand.class.equals(command.getPayloadType())) {
                LOGGER.info(String.format(INTERCEPTED_COMMAND, command.getPayloadType()));

                CreateUserCommand createUserCommand = (CreateUserCommand) command.getPayload();

                String userId = createUserCommand.getUserId();
                String email = createUserCommand.getEmail();
                String phoneNumber = createUserCommand.getPhoneNumber();

                UserLookupEntity userLookupEntity = userLookupRepository.findByUserIdOrEmailOrPhoneNumber(
                        userId, email, phoneNumber);

                throwExceptionIfEntityDoesExist(userLookupEntity,
                        String.format(USER_WITH_ID_EMAIL_OR_PHONE_NUMBER_ALREADY_EXISTS, userId, email, phoneNumber));
            }

            return command;
        };
    }
}
