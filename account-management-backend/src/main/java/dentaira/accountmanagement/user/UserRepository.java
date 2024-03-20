package dentaira.accountmanagement.user;

import dentaira.accountmanagement.common.EmailAddress;

import java.util.Optional;

public interface UserRepository {

    UserId generateId();

    Optional<User> findById(UserId userId);

    Optional<User> findByEmail(EmailAddress email);

    void save(User user);

    User update(User user);

}
