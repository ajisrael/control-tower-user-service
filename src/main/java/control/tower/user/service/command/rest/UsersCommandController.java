package control.tower.user.service.command.rest;

import control.tower.user.service.command.commands.CreateUserCommand;
import control.tower.user.service.command.commands.RemoveUserCommand;
import control.tower.user.service.command.rest.requests.CreateUserRequestModel;
import control.tower.user.service.command.rest.requests.RemoveUserRequestModel;
import control.tower.user.service.command.rest.responses.UserCreatedResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "User Command API")
public class UsersCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create user")
    public UserCreatedResponseModel createUser(@Valid @RequestBody CreateUserRequestModel createUserRequestModel) {
        CreateUserCommand createUserCommand = CreateUserCommand.builder()
                .userId(UUID.randomUUID().toString())
                .firstName(createUserRequestModel.getFirstName())
                .lastName(createUserRequestModel.getLastName())
                .email(createUserRequestModel.getEmail())
                .phoneNumber(createUserRequestModel.getPhoneNumber())
                .userRole(createUserRequestModel.getUserRole())
                .build();

        String userId = commandGateway.sendAndWait(createUserCommand);

        return UserCreatedResponseModel.builder().userId(userId).build();
    }

    @DeleteMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove user")
    public void removeUser(@Valid @RequestBody RemoveUserRequestModel removeUserRequestModel) {
        RemoveUserCommand removeUserCommand = RemoveUserCommand.builder()
                .userId(removeUserRequestModel.getUserId())
                .build();

        commandGateway.sendAndWait(removeUserCommand);
    }
}


