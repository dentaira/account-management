package dentaira.accountmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;

@SpringBootApplication
@Modulithic(sharedModules = {"common"})
public class AccountManagementBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccountManagementBackendApplication.class, args);
  }
}
