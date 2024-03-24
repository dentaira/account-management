package dentaira.accountmanagement.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import dentaira.accountmanagement.common.DateTimeFactory;
import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.user.UserId;
import dentaira.accountmanagement.user.UserRole;
import dentaira.accountmanagement.user.UserStatus;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

@MockitoSettings
class UserServiceTest {

  @Mock private DateTimeFactory dateTimeFactory;

  @InjectMocks private UserService sut;

  @Nested
  class ユーザー編集 {

    @Test
    public void 名前と権限が指定した値に更新される() {
      // given
      var source =
          defaultUser().toBuilder()
              .withName("oldName")
              .withRole(UserRole.Normal)
              .withUpdatedAt(Instant.ofEpochSecond(1234567890))
              .build();

      when(dateTimeFactory.now()).thenReturn(Instant.ofEpochSecond(1234567899));

      // when
      var actual = sut.edit(source, "newName", UserRole.Admin, false);

      // then
      assertThat(actual)
          .isEqualTo(
              new User(
                  source.userId(),
                  source.memberId(),
                  source.email(),
                  source.password(),
                  "newName",
                  UserRole.Admin,
                  source.status(),
                  source.version(),
                  source.createdAt(),
                  Instant.ofEpochSecond(1234567899)));
    }

    @Test
    public void 有効化した場合はステータスがActiveに更新される() {
      // given
      var source = defaultUser().toBuilder().withStatus(UserStatus.Inactive).build();

      when(dateTimeFactory.now()).thenReturn(Instant.ofEpochSecond(1234567899));

      // when
      var actual = sut.edit(source, source.name(), source.role(), true);

      // then
      assertThat(actual)
          .isEqualTo(
              new User(
                  source.userId(),
                  source.memberId(),
                  source.email(),
                  source.password(),
                  source.name(),
                  source.role(),
                  UserStatus.Active,
                  source.version(),
                  source.createdAt(),
                  Instant.ofEpochSecond(1234567899)));
    }

    @Test
    public void 無効化した場合はステータスがInactiveに更新される() {
      // given
      var source = defaultUser().toBuilder().withStatus(UserStatus.Inactive).build();

      when(dateTimeFactory.now()).thenReturn(Instant.ofEpochSecond(1234567899));

      // when
      var actual = sut.edit(source, source.name(), source.role(), false);

      // then
      assertThat(actual)
          .isEqualTo(
              new User(
                  source.userId(),
                  source.memberId(),
                  source.email(),
                  source.password(),
                  source.name(),
                  source.role(),
                  UserStatus.Inactive,
                  source.version(),
                  source.createdAt(),
                  Instant.ofEpochSecond(1234567899)));
    }

    @Test
    public void ステータス遷移が無効な場合は例外発生() {
      var source = defaultUser().toBuilder().withStatus(UserStatus.Init).build();
      assertThatThrownBy(() -> sut.edit(source, source.name(), source.role(), false));
    }
  }

  private User defaultUser() {
    return new User(
        UserId.of(UUID.randomUUID()),
        MemberId.of(UUID.randomUUID()),
        EmailAddress.of("local", "example.com"),
        "secret",
        "oldName",
        UserRole.Normal,
        UserStatus.Inactive,
        0,
        Instant.EPOCH,
        Instant.EPOCH);
  }
}
