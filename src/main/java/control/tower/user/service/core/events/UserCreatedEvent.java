package control.tower.user.service.core.events;

import control.tower.user.service.core.models.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreatedEvent {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole userRole;
}
