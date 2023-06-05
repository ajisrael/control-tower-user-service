package control.tower.user.service.command.rest;

import control.tower.user.service.core.models.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CreateUserRestModel {

    @NotBlank(message = "First name is a required field")
    private String firstName;
    @NotBlank(message = "Last name is a required field")
    private String lastName;
    @NotBlank(message = "Email is a required field")
    private String email;
    @NotBlank(message = "Phone number is a required field")
    private String phoneNumber;
    @NotNull(message = "User role is a required field")
    private UserRole userRole;
}
