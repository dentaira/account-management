package dentaira.accountmanagement.user.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.common.EntityUpdateConflictException;
import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.user.UserId;
import dentaira.accountmanagement.user.UserRole;
import dentaira.accountmanagement.user.UserStatus;
import dentaira.accountmanagement.user.domain.User;
import java.time.Instant;
import java.util.UUID;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@JooqTest
class UserRepositoryImplIT {

  @Container @ServiceConnection
  static PostgreSQLContainer<?> postgresql =
      new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

  @Autowired DSLContext context;

  @MockBean TimeBasedEpochGenerator timeBasedEpochGenerator;

  UserRepositoryImpl sut;

  @BeforeEach
  void setUp() {
    sut = new UserRepositoryImpl(timeBasedEpochGenerator, context);
  }

  @Test
  void Userの保存と取得ができる() {
    // 登録
    var userId = UserId.of(UUID.randomUUID());
    var source = createUser(userId, EmailAddress.of("local", "example.com"), 1);
    sut.save(source);

    // IDで取得
    assertThat(sut.findById(userId)).hasValue(source);

    // 更新
    var email2 = EmailAddress.of("local2", "example.com");
    var updated =
        sut.update(
            new User(
                userId,
                new MemberId(UUID.randomUUID()),
                email2,
                "newPassword",
                "newName",
                UserRole.Admin,
                UserStatus.Active,
                1,
                Instant.EPOCH,
                Instant.MAX));
    assertThat(updated.version()).isEqualTo(2);

    // emailで取得
    assertThat(sut.findByEmail(email2)).hasValue(updated);
  }

  @Test
  void 更新時に渡したデータと保存済みデータのversionが異なる場合は例外発生() {
    // given
    var userId = UserId.of(UUID.randomUUID());

    // version=2で保存済みの状態で
    var source = createUser(userId, EmailAddress.of("local", "example.com"), 2);
    sut.save(source);

    // version=1で更新しようとする
    var updated =
        new User(
            userId,
            new MemberId(UUID.randomUUID()),
            EmailAddress.of("local2", "example.com"),
            "newPassword",
            "newName",
            UserRole.Admin,
            UserStatus.Active,
            1, // versionが異なる
            Instant.EPOCH,
            Instant.MAX);

    // when and then
    assertThatThrownBy(() -> sut.update(updated)).isInstanceOf(EntityUpdateConflictException.class);
  }

  @Test
  void 更新時に保存済みデータが存在しない場合は例外発生() {
    // given
    var userId = UserId.of(UUID.randomUUID());
    var updated =
        new User(
            userId,
            new MemberId(UUID.randomUUID()),
            EmailAddress.of("local2", "example.com"),
            "newPassword",
            "newName",
            UserRole.Admin,
            UserStatus.Active,
            1, // versionが異なる
            Instant.EPOCH,
            Instant.MAX);

    // when and then
    assertThatThrownBy(() -> sut.update(updated)).isInstanceOf(EntityUpdateConflictException.class);
  }

  private User createUser(UserId userId, EmailAddress email, int version) {
    return new User(
        userId,
        MemberId.of(UUID.randomUUID()),
        email,
        "secret",
        "oldName",
        UserRole.Normal,
        UserStatus.Inactive,
        version,
        Instant.EPOCH,
        Instant.EPOCH);
  }
}
