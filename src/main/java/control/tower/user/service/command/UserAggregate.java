package control.tower.user.service.command;

import control.tower.user.service.command.commands.CreateUserCommand;
import control.tower.user.service.command.commands.RemoveUserCommand;
import control.tower.user.service.core.events.UserCreatedEvent;
import control.tower.user.service.core.events.UserRemovedEvent;
import control.tower.user.service.core.models.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

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
        command.validate();

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

    @CommandHandler
    public void handle(RemoveUserCommand command) {
        command.validate();

        UserRemovedEvent event = UserRemovedEvent.builder()
                .userId(command.getUserId())
                .email(email)
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent event) {
        this.userId = event.getUserId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.email = event.getEmail();
        this.phoneNumber = event.getPhoneNumber();
        this.userRole = event.getUserRole();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
