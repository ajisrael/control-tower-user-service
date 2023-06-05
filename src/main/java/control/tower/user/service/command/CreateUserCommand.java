package control.tower.user.service.command;

import control.tower.user.service.core.models.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Builder
public class CreateUserCommand {

    @TargetAggregateIdentifier
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole userRole;
}
