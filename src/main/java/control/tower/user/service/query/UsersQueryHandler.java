package control.tower.user.service.query;

import control.tower.user.service.core.data.UserEntity;
import control.tower.user.service.core.data.UserRepository;
import control.tower.user.service.query.queries.FindAllUsersQuery;
import control.tower.user.service.query.queries.FindUserQuery;
import control.tower.user.service.query.querymodels.UserQueryModel;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static control.tower.user.service.core.constants.ExceptionMessages.USER_WITH_ID_DOES_NOT_EXIST;

@Component
@AllArgsConstructor
public class UsersQueryHandler {

    private final UserRepository userRepository;

    @QueryHandler
    public List<UserQueryModel> findAllUsers(FindAllUsersQuery query) {
        List<UserEntity> userEntities = userRepository.findAll();
        return convertUserEntitiesToUserQueryModels(userEntities);
    }

    @QueryHandler
    public UserQueryModel findUser(FindUserQuery query) {
        UserEntity userEntity = userRepository.findById(query.getUserId()).orElseThrow(
                () -> new IllegalStateException(String.format(USER_WITH_ID_DOES_NOT_EXIST, query.getUserId())));
        return convertUserEntityToUserQueryModel(userEntity);
    }

    private List<UserQueryModel> convertUserEntitiesToUserQueryModels(
            List<UserEntity> userEntities) {
        List<UserQueryModel> userQueryModels = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            userQueryModels.add(convertUserEntityToUserQueryModel(userEntity));
        }

        return userQueryModels;
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
