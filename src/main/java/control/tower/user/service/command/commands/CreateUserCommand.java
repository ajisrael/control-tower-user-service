package control.tower.user.service.command.commands;

import control.tower.user.service.core.models.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static control.tower.core.utils.Helper.isNullOrBlank;

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

    public void validate() {
        if (isNullOrBlank(this.getUserId())) {
            throw new IllegalArgumentException("UserId cannot be empty");
        }

        if (isNullOrBlank(this.getFirstName())) {
            throw new IllegalArgumentException("First name cannot be empty");
        }

        if (isNullOrBlank(this.getLastName())) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }

        if (isNullOrBlank(this.getEmail())) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (isNullOrBlank(this.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        if (this.getUserRole() == null) {
            throw new IllegalArgumentException("User role cannot be null");
        }
    }
}
