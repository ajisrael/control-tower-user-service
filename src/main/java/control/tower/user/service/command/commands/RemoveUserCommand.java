package control.tower.user.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static control.tower.core.utils.Helper.isNullOrBlank;

@Getter
@Builder
public class RemoveUserCommand {

    @TargetAggregateIdentifier
    private String userId;

    public void validate() {
        if (isNullOrBlank(this.getUserId())) {
            throw new IllegalArgumentException("UserId cannot be empty");
        }
    }
}
