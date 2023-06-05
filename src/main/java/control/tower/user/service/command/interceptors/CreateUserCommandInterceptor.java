package control.tower.user.service.command.interceptors;

import control.tower.user.service.command.CreateUserCommand;
import control.tower.user.service.core.data.UserLookupEntity;
import control.tower.user.service.core.data.UserLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

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

            LOGGER.info("Intercepted command: " + command.getPayloadType());

            if (CreateUserCommand.class.equals(command.getPayloadType())) {

                CreateUserCommand createUserCommand = (CreateUserCommand) command.getPayload();

                UserLookupEntity userLookupEntity = userLookupRepository.findByUserId(
                        createUserCommand.getUserId());

                if (userLookupEntity != null) {
                    throw new IllegalStateException(
                            String.format("User with id %s already exists",
                                    createUserCommand.getUserId())
                    );
                }
            }

            return command;
        };
    }
}
