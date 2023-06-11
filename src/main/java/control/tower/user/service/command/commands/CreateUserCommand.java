package control.tower.user.service.command.commands;

import control.tower.user.service.core.models.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static control.tower.core.utils.Helper.*;
import static control.tower.user.service.core.constants.ExceptionMessages.*;

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
        throwExceptionIfParameterIsEmpty(this.getUserId(), USER_ID_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getFirstName(), FIRST_NAME_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getLastName(), LAST_NAME_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getEmail(), EMAIL_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsEmpty(this.getPhoneNumber(), PHONE_NUMBER_CANNOT_BE_EMPTY);
        throwExceptionIfParameterIsNull(this.getUserRole(), USER_ROLE_CANNOT_BE_NULL);
    }
}
