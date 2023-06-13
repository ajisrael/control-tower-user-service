package control.tower.user.service.command.interceptors;

import control.tower.user.service.command.commands.RemoveUserCommand;
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
import static control.tower.core.utils.Helper.throwExceptionIfEntityDoesNotExist;
import static control.tower.user.service.core.constants.ExceptionMessages.USER_WITH_ID_DOES_NOT_EXIST;

@Component
public class RemoveUserCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveUserCommandInterceptor.class);

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

                UserLookupEntity userLookupEntity = userLookupRepository.findByUserId(removeUserCommand.getUserId());

                throwExceptionIfEntityDoesNotExist(userLookupEntity,
                        String.format(USER_WITH_ID_DOES_NOT_EXIST, removeUserCommand.getUserId()));
            }

            return command;
        };
    }
}
