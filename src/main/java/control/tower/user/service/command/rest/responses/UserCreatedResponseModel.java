package control.tower.user.service.command.rest.responses;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreatedResponseModel {

    private String userId;
}
