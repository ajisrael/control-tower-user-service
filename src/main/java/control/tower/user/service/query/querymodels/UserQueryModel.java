package control.tower.user.service.query.querymodels;

import control.tower.user.service.core.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserQueryModel {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole userRole;
}
