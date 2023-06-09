package control.tower.user.service.query.rest;

import control.tower.user.service.core.data.UserEntity;
import control.tower.user.service.query.queries.FindAllUsersQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    public List<UserRestModel> getUsers() {
        FindAllUsersQuery findAllUsersQuery = new FindAllUsersQuery();

        List<UserEntity> userEntities = queryGateway.query(findAllUsersQuery,
                ResponseTypes.multipleInstancesOf(UserEntity.class)).join();

        return convertUserEntitiesToUserRestModels(userEntities);
    }

    private List<UserRestModel> convertUserEntitiesToUserRestModels(
            List<UserEntity> userEntities) {
        List<UserRestModel> userRestModels = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            userRestModels.add(new UserRestModel(
                    userEntity.getUserId(),
                    userEntity.getFirstName(),
                    userEntity.getLastName(),
                    userEntity.getEmail(),
                    userEntity.getPhoneNumber(),
                    userEntity.getUserRole()
            ));
        }

        return userRestModels;
    }
}
