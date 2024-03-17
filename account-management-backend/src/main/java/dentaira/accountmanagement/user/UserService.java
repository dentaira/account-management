package dentaira.accountmanagement.user;

import dentaira.accountmanagement.common.datetime.DateTimeFactory;
import dentaira.accountmanagement.common.email.EmailAddress;
import dentaira.accountmanagement.common.entity.EntityId;
import dentaira.accountmanagement.common.entity.EntityIdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final EntityIdGenerator entityIdGenerator;

    private final DateTimeFactory dateTimeFactory;

    public User create(EmailAddress email, String name, UserRole role) {
        EntityId<User> userId = entityIdGenerator.generate();
        return new User(userId, email, name, role, UserStatus.Active, 1, dateTimeFactory.now(), dateTimeFactory.now());
    }

    public User edit(User user, UserEdit edit) {
        if (user.status().canTransitTo(edit.status())) {
            throw new IllegalArgumentException("Invalid status transition. " + user.status() + " -> " + edit.status());
        }
        return user
                .withName(edit.name())
                .withRole(edit.role())
                .withStatus(edit.status())
                .withUpdatedAt(dateTimeFactory.now());
    }

    public record UserEdit(String name, UserRole role, UserStatus status) {
    }

    public User changeEmail(User user, EmailAddress email) {
        return user.withEmail(email).withUpdatedAt(dateTimeFactory.now());
    }
}



