package dentaira.accountmanagement.user;

import dentaira.accountmanagement.common.EmailAddress;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * ユーザーに登録されていないメールアドレス
 *
 * <p>publicなrecordはコンストラクタの可視性をパッケージプライベートにできないため、代わりにlombokの@Valueを使用している
 */
@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Accessors(fluent = true)
public class UnregisteredUserEmailAddress {
  EmailAddress email;
}
