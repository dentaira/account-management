package dentaira.accountmanagement.user;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.entity.EntityId;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(EntityId<User> userId);

    Optional<User> findByEmail(EmailAddress email);

    void save(User user);

    User update(User user);

}
