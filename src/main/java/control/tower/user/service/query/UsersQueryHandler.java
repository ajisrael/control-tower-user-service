package control.tower.user.service.query;

import control.tower.user.service.core.data.UserEntity;
import control.tower.user.service.core.data.UserRepository;
import control.tower.user.service.query.queries.FindAllUsersQuery;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UsersQueryHandler {

    private final UserRepository userRepository;

    @QueryHandler
    public List<UserEntity> findAllUsers(FindAllUsersQuery query) {
        return userRepository.findAll();
    }
}
