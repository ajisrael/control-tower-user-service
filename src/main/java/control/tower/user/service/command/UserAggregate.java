package control.tower.user.service.command;

import control.tower.user.service.core.events.UserCreatedEvent;
import control.tower.user.service.core.models.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import static control.tower.user.service.core.utils.Helper.isNullOrBlank;

@Aggregate
@NoArgsConstructor
@Getter
public class UserAggregate {

    @AggregateIdentifier
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole userRole;

    @CommandHandler
    public UserAggregate(CreateUserCommand command) {
        validateCreateUserCommand(command);

        UserCreatedEvent event = UserCreatedEvent.builder()
                .userId(command.getUserId())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .phoneNumber(command.getPhoneNumber())
                .userRole(command.getUserRole())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventHandler
    public void on(UserCreatedEvent event) {
        this.userId = event.getUserId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.email = event.getEmail();
        this.phoneNumber = event.getPhoneNumber();
        this.userRole = event.getUserRole();
    }

    private void validateCreateUserCommand(CreateUserCommand command) {
        if (isNullOrBlank(command.getUserId())) {
            throw new IllegalArgumentException("UserId cannot be empty");
        }

        if (isNullOrBlank(command.getFirstName())) {
            throw new IllegalArgumentException("First name cannot be empty");
        }

        if (isNullOrBlank(command.getLastName())) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }

        if (isNullOrBlank(command.getEmail())) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (isNullOrBlank(command.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        if (command.getUserRole() == null) {
            throw new IllegalArgumentException("User role cannot be null");
        }
    }
}
