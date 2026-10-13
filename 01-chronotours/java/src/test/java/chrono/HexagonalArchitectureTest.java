package chrono;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "chrono", importOptions = ImportOption.DoNotIncludeTests.class)
class HexagonalArchitectureTest {
    @ArchTest
    static final ArchRule hexagone = onionArchitecture()
            .withOptionalLayers(true)
            .domainModels("chrono.domain..")
            .applicationServices("chrono.application..")
            .adapter("web", "chrono.infrastructure.web..")
            .adapter("persistence", "chrono.infrastructure.persistence..")
            .adapter("config", "chrono.infrastructure.config..");

    @ArchTest
    static final ArchRule le_coeur_ignore_les_frameworks = noClasses()
            .that().resideInAnyPackage("chrono.domain..", "chrono.application..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "org.springframework..", "jakarta..", "org.mapstruct..");
}
