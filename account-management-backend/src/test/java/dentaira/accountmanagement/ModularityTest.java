package dentaira.accountmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModularityTest {

  ApplicationModules modules = ApplicationModules.of(AccountManagementBackendApplication.class);

  @Test
  void verify() {
    modules.forEach(System.out::println);
    modules.verify();
  }

  @Test
  void writeDocumentationSnippets() {
    new Documenter(modules).writeModulesAsPlantUml().writeIndividualModulesAsPlantUml();
  }
}
