package control.tower.user.service.query.rest;

import control.tower.user.service.query.queries.FindAllUsersQuery;
import control.tower.user.service.query.queries.FindUserQuery;
import control.tower.user.service.query.querymodels.UserQueryModel;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    public List<UserQueryModel> getUsers() {
        return queryGateway.query(new FindAllUsersQuery(),
                ResponseTypes.multipleInstancesOf(UserQueryModel.class)).join();
    }

    @GetMapping(params = "userId")
    public UserQueryModel getUser(String userId) {
        return queryGateway.query(new FindUserQuery(userId),
                ResponseTypes.instanceOf(UserQueryModel.class)).join();
    }
}
