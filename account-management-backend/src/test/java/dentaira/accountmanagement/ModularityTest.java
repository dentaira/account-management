package dentaira.accountmanagement;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModularityTest {

  ApplicationModules modules = ApplicationModules.of(AccountManagementBackendApplication.class);

  @Test
  void verifyModules() {
    modules.forEach(System.out::println);
    modules.verify();
  }

  @Test
  void writeDocumentationSnippets() {
    new Documenter(modules).writeModulesAsPlantUml().writeIndividualModulesAsPlantUml();
  }

  @Test
  void verifyLayer() {
    JavaClasses classes = new ClassFileImporter().importPackages("dentaira.accountmanagement");
    Architectures.onionArchitecture()
        .applicationServices("dentaira.accountmanagement.query")
        .adapter("web", "dentaira.accountmanagement.adapter.web")
        .adapter("infra", "dentaira.accountmanagement..infra..")
        .withOptionalLayers(true)
        .check(classes);
  }
}
