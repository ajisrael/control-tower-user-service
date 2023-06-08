package control.tower.user.service.command.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class RemoveUserRestModel {

    @NotBlank(message = "User Id is a required field")
    private String userId;
}
