package dentaira.accountmanagement.user;

import dentaira.accountmanagement.common.email.EmailAddress;
import dentaira.accountmanagement.common.entity.EntityId;
import lombok.Value;
import lombok.With;
import lombok.experimental.Accessors;

import java.time.Instant;

@Value
@Accessors(fluent = true)
@With
public class User {
    EntityId<User> userId;
    EmailAddress email;
    String name;
    UserRole role;
    UserStatus status;
    int version;
    Instant createdAt;
    Instant updatedAt;
}
