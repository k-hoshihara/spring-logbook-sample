package payroll;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@SuppressWarnings("unused")
@AnalyzeClasses(packages = "payroll", importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    @ArchTest
    static final ArchRule controller_should_not_access_repository =
        noClasses()
            .that().resideInAPackage("..controller..")
            .should().dependOnClassesThat().resideInAPackage("..repository..")
            .as("ControllerはRepositoryに直接アクセスしてはならない");

    @ArchTest
    static final ArchRule controller_should_not_access_entity =
        noClasses()
            .that().resideInAPackage("..controller..")
            .should().dependOnClassesThat().resideInAPackage("..entity..")
            .as("ControllerはEntityに直接アクセスしてはならない");

    // カスタム例外のControllerでのcatchを防ぐ。
    // IllegalStateException等の標準例外は検出できないため、完全ではない。
    @ArchTest
    static final ArchRule controller_should_not_access_exception =
        noClasses()
            .that().resideInAPackage("..controller..")
            .should().dependOnClassesThat().resideInAPackage("..exception..")
            .as("Controllerは例外を直接ハンドリングせずRestControllerAdviceに委譲する");
}
