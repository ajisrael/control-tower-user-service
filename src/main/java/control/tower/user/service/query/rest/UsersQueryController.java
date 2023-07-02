package control.tower.user.service.query.rest;

import control.tower.user.service.query.queries.FindAllUsersQuery;
import control.tower.user.service.query.queries.FindUserQuery;
import control.tower.user.service.query.querymodels.UserQueryModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User Query API")
public class UsersQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get users")
    public List<UserQueryModel> getUsers() {
        return queryGateway.query(new FindAllUsersQuery(),
                ResponseTypes.multipleInstancesOf(UserQueryModel.class)).join();
    }

    @GetMapping(params = "userId")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get user by id")
    public UserQueryModel getUser(String userId) {
        return queryGateway.query(new FindUserQuery(userId),
                ResponseTypes.instanceOf(UserQueryModel.class)).join();
    }
}
