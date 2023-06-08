package control.tower.user.service.command;

import control.tower.user.service.core.data.UserLookupEntity;
import control.tower.user.service.core.data.UserLookupRepository;
import control.tower.user.service.core.events.UserCreatedEvent;
import control.tower.user.service.core.events.UserRemovedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import static control.tower.core.utils.Helper.throwErrorIfEntityDoesNotExist;

@Component
@AllArgsConstructor
@ProcessingGroup("user-group")
public class UserLookupEventsHandler {

    private UserLookupRepository userLookupRepository;

    @EventHandler
    public void on(UserCreatedEvent event) {
        userLookupRepository.save(
                new UserLookupEntity(
                        event.getUserId(),
                        event.getEmail(),
                        event.getPhoneNumber()
                )
        );
    }

    @EventHandler
    public void on(UserRemovedEvent event) {
        UserLookupEntity userLookupEntity = userLookupRepository.findByUserId(event.getUserId());
        throwErrorIfEntityDoesNotExist(userLookupEntity, String.format("User %s does not exist", event.getUserId()));
        userLookupRepository.delete(userLookupEntity);
    }
}
