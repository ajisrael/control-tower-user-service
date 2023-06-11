package control.tower.user.service.query;

import control.tower.user.service.core.data.UserEntity;
import control.tower.user.service.core.data.UserRepository;
import control.tower.user.service.query.queries.FindAllUsersQuery;
import control.tower.user.service.query.queries.FindUserQuery;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

import static control.tower.user.service.core.constants.ExceptionMessages.USER_WITH_ID_DOES_NOT_EXIST;

@Component
@AllArgsConstructor
public class UsersQueryHandler {

    private final UserRepository userRepository;

    @QueryHandler
    public List<UserEntity> findAllUsers(FindAllUsersQuery query) {
        return userRepository.findAll();
    }

    @QueryHandler
    public UserEntity findUser(FindUserQuery query) {
        return userRepository.findById(query.getUserId()).orElseThrow(
                () -> new IllegalStateException(String.format(USER_WITH_ID_DOES_NOT_EXIST, query.getUserId())));
    }
}
