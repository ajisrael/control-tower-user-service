package control.tower.user.service.core.data;

import control.tower.user.service.core.models.UserRole;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name="users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 789654123987456323L;

    @Id
    @Column(unique = true)
    private String userId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private UserRole userRole;
}
