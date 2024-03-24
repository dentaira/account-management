package dentaira.accountmanagement.user;

import static dentaira.accountmanagement.user.UserStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class UserStatusTest {

  @Nested
  class 有効なステータス遷移の確認 {

    @Nested
    class Initから {

      private final UserStatus sut = Init;

      @ParameterizedTest
      @EnumSource(
          value = UserStatus.class,
          names = {"Init", "Active"})
      void 遷移できる(UserStatus to) {
        assertThat(sut.canTransitTo(to)).isEqualTo(true);
      }

      @ParameterizedTest
      @EnumSource(
          value = UserStatus.class,
          names = {"Init", "Active"},
          mode = EnumSource.Mode.EXCLUDE)
      void 遷移できない(UserStatus to) {
        assertThat(sut.canTransitTo(to)).isEqualTo(false);
      }
    }

    @Nested
    class Activeから {

      private final UserStatus sut = Active;

      @ParameterizedTest
      @EnumSource(
          value = UserStatus.class,
          names = {"Active", "Inactive", "Deleted"})
      void 遷移できる(UserStatus to) {
        assertThat(sut.canTransitTo(to)).isEqualTo(true);
      }

      @ParameterizedTest
      @EnumSource(
          value = UserStatus.class,
          names = {"Active", "Inactive", "Deleted"},
          mode = EnumSource.Mode.EXCLUDE)
      void 遷移できない(UserStatus to) {
        assertThat(sut.canTransitTo(to)).isEqualTo(false);
      }
    }

    @Nested
    class Inactiveから {

      private final UserStatus sut = Inactive;

      @ParameterizedTest
      @EnumSource(
          value = UserStatus.class,
          names = {"Active", "Inactive", "Deleted"})
      void 遷移できる(UserStatus to) {
        assertThat(sut.canTransitTo(to)).isEqualTo(true);
      }

      @ParameterizedTest
      @EnumSource(
          value = UserStatus.class,
          names = {"Active", "Inactive", "Deleted"},
          mode = EnumSource.Mode.EXCLUDE)
      void 遷移できない(UserStatus to) {
        assertThat(sut.canTransitTo(to)).isEqualTo(false);
      }
    }

    @Nested
    class Deletedから {

      private final UserStatus sut = Deleted;

      @ParameterizedTest
      @EnumSource(
          value = UserStatus.class,
          names = {"Deleted"})
      void 遷移できる(UserStatus to) {
        assertThat(sut.canTransitTo(to)).isEqualTo(true);
      }

      @ParameterizedTest
      @EnumSource(
          value = UserStatus.class,
          names = {"Deleted"},
          mode = EnumSource.Mode.EXCLUDE)
      void 遷移できない(UserStatus to) {
        assertThat(sut.canTransitTo(to)).isEqualTo(false);
      }
    }
  }
}
