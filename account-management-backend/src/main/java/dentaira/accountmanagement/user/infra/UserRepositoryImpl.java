package dentaira.accountmanagement.user.infra;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.exception.EntityUpdateConflictException;
import dentaira.accountmanagement.jooq.UsersRecord;
import dentaira.accountmanagement.user.UserId;
import dentaira.accountmanagement.user.UserRole;
import dentaira.accountmanagement.user.UserStatus;
import dentaira.accountmanagement.user.domain.User;
import dentaira.accountmanagement.user.domain.UserRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Function;

import static dentaira.accountmanagement.jooq.Tables.USERS;


@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final TimeBasedEpochGenerator generator = Generators.timeBasedEpochGenerator();
    private final DSLContext context;

    @Override
    public UserId generateId() {
        return new UserId(generator.generate());
    }

    @Override
    public Optional<User> findById(UserId userId) {
        var record = context.selectFrom(USERS).where(USERS.USER_ID.eq(userId.value())).fetchOptional();
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
                .set(USERS.USER_ID, user.userId().value())
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
                .where(USERS.USER_ID.eq(user.userId().value()))
                .and(USERS.VERSION.eq(user.version()))
                .returning()
                .fetchOptional()
                .map(toUser).orElseThrow(EntityUpdateConflictException::new);
    }

    private final Function<UsersRecord, User> toUser = r -> new User(
            new UserId(r.getUserId()),
            new EmailAddress(r.getEmail()),
            r.getUserName(),
            UserRole.valueOf(r.getRole()),
            UserStatus.valueOf(r.getStatus()),
            r.getVersion(),
            r.getCreatedAt(),
            r.getUpdatedAt()
    );

}
