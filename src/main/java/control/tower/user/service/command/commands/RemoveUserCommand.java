package control.tower.user.service.command.commands;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static control.tower.core.utils.Helper.throwExceptionIfParameterIsEmpty;
import static control.tower.user.service.core.constants.ExceptionMessages.USER_ID_CANNOT_BE_EMPTY;

@Getter
@Builder
public class RemoveUserCommand {

    @TargetAggregateIdentifier
    private String userId;

    public void validate() {
        throwExceptionIfParameterIsEmpty(this.getUserId(), USER_ID_CANNOT_BE_EMPTY);
    }
}
