package control.tower.user.service.query;

import control.tower.user.service.core.data.UserEntity;
import control.tower.user.service.core.data.UserRepository;
import control.tower.user.service.core.events.UserCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("inventory-item-group")
public class UserEventsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventsHandler.class);

    private final UserRepository userRepository;

    public UserEventsHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception) throws Exception {
        throw exception;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception) {
        LOGGER.error(exception.getLocalizedMessage());
    }

    @EventHandler
    public void on(UserCreatedEvent event) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(event, userEntity);
        userRepository.save(userEntity);
    }
}
