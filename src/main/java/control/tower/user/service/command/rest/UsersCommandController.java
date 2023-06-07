package control.tower.user.service.command.rest;

import control.tower.user.service.command.commands.CreateUserCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String createUser(@Valid @RequestBody CreateUserRestModel createUserRestModel) {
        CreateUserCommand createUserCommand = CreateUserCommand.builder()
                .userId(UUID.randomUUID().toString())
                .firstName(createUserRestModel.getFirstName())
                .lastName(createUserRestModel.getLastName())
                .email(createUserRestModel.getEmail())
                .phoneNumber(createUserRestModel.getPhoneNumber())
                .userRole(createUserRestModel.getUserRole())
                .build();

        return commandGateway.sendAndWait(createUserCommand);
    }
}


