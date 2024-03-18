package dentaira.accountmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModularityTests {

    @Test
    void verify() {
        var modules = ApplicationModules.of(AccountManagementBackendApplication.class);
        modules.forEach(System.out::println);
        modules.verify();
    }


}