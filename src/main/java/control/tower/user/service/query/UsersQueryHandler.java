package control.tower.user.service.query;

import control.tower.core.query.queries.DoesUserExistQuery;
import control.tower.user.service.core.data.UserEntity;
import control.tower.user.service.core.data.UserRepository;
import control.tower.user.service.query.queries.FindAllUsersQuery;
import control.tower.user.service.query.queries.FindUserQuery;
import control.tower.user.service.query.querymodels.UserQueryModel;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static control.tower.user.service.core.constants.ExceptionMessages.USER_WITH_ID_DOES_NOT_EXIST;

@Component
@AllArgsConstructor
public class UsersQueryHandler {

    private final UserRepository userRepository;

    @QueryHandler
    public Page<UserQueryModel> findAllUsers(FindAllUsersQuery query) {
        return userRepository.findAll(query.getPageable())
                .map(this::convertUserEntityToUserQueryModel);
    }

    @QueryHandler
    public UserQueryModel findUser(FindUserQuery query) {
        UserEntity userEntity = userRepository.findById(query.getUserId()).orElseThrow(
                () -> new IllegalStateException(String.format(USER_WITH_ID_DOES_NOT_EXIST, query.getUserId())));
        return convertUserEntityToUserQueryModel(userEntity);
    }

    @QueryHandler
    public boolean doesUserExist(DoesUserExistQuery query) {
        return userRepository.findById(query.getUserId()).isPresent();
    }

    private UserQueryModel convertUserEntityToUserQueryModel(UserEntity userEntity) {
        return new UserQueryModel(
                userEntity.getUserId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getPhoneNumber(),
                userEntity.getUserRole()
        );
    }
}
