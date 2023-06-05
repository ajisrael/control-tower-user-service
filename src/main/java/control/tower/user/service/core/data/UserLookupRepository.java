package control.tower.user.service.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLookupRepository extends JpaRepository<UserLookupEntity, String> {

    UserLookupEntity findByUserId(String userId);

    UserLookupEntity findByUserIdOrEmailOrPhoneNumber(String userId, String email, String phoneNumber);
}
