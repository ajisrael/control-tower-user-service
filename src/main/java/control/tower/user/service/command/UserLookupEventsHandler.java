package control.tower.user.service.command;

import control.tower.user.service.core.data.UserLookupEntity;
import control.tower.user.service.core.data.UserLookupRepository;
import control.tower.user.service.core.events.UserCreatedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

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
}
