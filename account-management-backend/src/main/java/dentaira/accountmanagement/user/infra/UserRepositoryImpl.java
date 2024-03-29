package dentaira.accountmanagement.user.infra;

import static dentaira.accountmanagement.jooq.Tables.USERS;

import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.common.EntityUpdateConflictException;
import dentaira.accountmanagement.jooq.UsersRecord;
import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.user.UserId;
import dentaira.accountmanagement.user.UserRole;
import dentaira.accountmanagement.user.UserStatus;
import dentaira.accountmanagement.user.domain.User;
import dentaira.accountmanagement.user.domain.UserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final TimeBasedEpochGenerator generator;
  private final DSLContext context;

  @Override
  public UserId generateId() {
    return new UserId(generator.generate());
  }

  @Override
  public Optional<User> findById(UserId userId) {
    var record = context.selectFrom(USERS).where(USERS.USER_ID.eq(userId.value())).fetchOptional();
    return record.map(this::toUser);
  }

  @Override
  public Optional<User> findByEmail(EmailAddress email) {
    var record = context.selectFrom(USERS).where(USERS.EMAIL.eq(email.value())).fetchOptional();
    return record.map(this::toUser);
  }

  @Override
  public void save(User user) {
    context
        .insertInto(USERS)
        .set(USERS.USER_ID, user.userId().value())
        .set(USERS.MEMBER_ID, user.memberId().value())
        .set(USERS.EMAIL, user.email().value())
        .set(USERS.PASSWORD, user.password())
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
    return context
        .update(USERS)
        .set(USERS.EMAIL, user.email().value())
        .set(USERS.PASSWORD, user.password())
        .set(USERS.USER_NAME, user.name())
        .set(USERS.ROLE, user.role().name())
        .set(USERS.STATUS, user.status().name())
        .set(USERS.VERSION, user.version() + 1)
        .where(USERS.USER_ID.eq(user.userId().value()))
        .and(USERS.VERSION.eq(user.version()))
        .returning()
        .fetchOptional()
        .map(this::toUser)
        .orElseThrow(EntityUpdateConflictException::new);
  }

  private User toUser(UsersRecord r) {
    return new User(
        new UserId(r.getUserId()),
        new MemberId(r.getMemberId()),
        new EmailAddress(r.getEmail()),
        r.getPassword(),
        r.getUserName(),
        UserRole.valueOf(r.getRole()),
        UserStatus.valueOf(r.getStatus()),
        r.getVersion(),
        r.getCreatedAt(),
        r.getUpdatedAt());
  }
}
