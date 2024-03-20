package dentaira.accountmanagement.user;

import dentaira.accountmanagement.common.DateTimeFactory;
import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.entity.EntityId;
import dentaira.accountmanagement.entity.EntityIdGenerator;
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

    public User edit(User user, String name, UserRole role, boolean activate) {
        var newStatus = activate ? UserStatus.Active : UserStatus.Inactive;
        if (!user.status().canTransitTo(newStatus)) {
            throw new IllegalArgumentException("Invalid status transition. " + user.status() + " -> " + newStatus);
        }
        return user.toBuilder()
                .withName(name)
                .withRole(role)
                .withStatus(newStatus)
                .withUpdatedAt(dateTimeFactory.now())
                .build();
    }

    public User changeEmail(User user, EmailAddress email) {
        return user.toBuilder().withEmail(email).withUpdatedAt(dateTimeFactory.now()).build();
    }
}



