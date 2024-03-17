package dentaira.accountmanagement.infra.db;

import dentaira.accountmanagement.common.email.EmailAddress;
import dentaira.accountmanagement.common.entity.EntityId;
import dentaira.accountmanagement.common.entity.EntityUpdateConflictException;
import dentaira.accountmanagement.generated.tables.records.UsersRecord;
import dentaira.accountmanagement.user.User;
import dentaira.accountmanagement.user.UserRepository;
import dentaira.accountmanagement.user.UserRole;
import dentaira.accountmanagement.user.UserStatus;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;

import static dentaira.accountmanagement.generated.Tables.USERS;


@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DSLContext context;

    @Override
    public Optional<User> findById(EntityId<User> userId) {
        var record = context.selectFrom(USERS).where(USERS.ID.eq(userId.value())).fetchOptional();
        return record.map(toUser);
    }

    @Override
    public Optional<User> findByEmail(EmailAddress email) {
        var record = context.selectFrom(USERS).where(USERS.EMAIL.eq(email.value())).fetchOptional();
        return record.map(toUser);
    }

    @Override
    public void save(User user) {
        context.insertInto(USERS)
                .set(USERS.ID, user.userId().value())
                .set(USERS.EMAIL, user.email().value())
                .set(USERS.USER_NAME, user.name())
                .set(USERS.ROLE, user.role().name())
                .set(USERS.STATUS, user.status().name())
                .set(USERS.VERSION, user.version())
                .set(USERS.CREATED_AT, user.createdAt())
                .set(USERS.UPDATED_AT, user.updatedAt())
                .execute();
    }

    @Override
    public User update(User user) {
        return context.update(USERS)
                .set(USERS.EMAIL, user.email().value())
                .set(USERS.USER_NAME, user.name())
                .set(USERS.ROLE, user.role().name())
                .set(USERS.STATUS, user.status().name())
                .set(USERS.VERSION, user.version() + 1)
                .where(USERS.ID.eq(user.userId().value()))
                .and(USERS.VERSION.eq(user.version()))
                .returning()
                .fetchOptional()
                .map(toUser).orElseThrow(EntityUpdateConflictException::new);
    }

    private final Function<UsersRecord, User> toUser = r -> new User(
            new EntityId<>(r.getId()),
            new EmailAddress(r.getEmail()),
            r.getUserName(),
            UserRole.valueOf(r.getRole()),
            UserStatus.valueOf(r.getStatus()),
            r.getVersion(),
            r.getCreatedAt(),
            r.getUpdatedAt()
    );

}
