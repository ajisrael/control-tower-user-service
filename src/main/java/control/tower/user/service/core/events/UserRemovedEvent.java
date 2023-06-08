package control.tower.user.service.core.events;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRemovedEvent {

    private String userId;
    private String email;
}
