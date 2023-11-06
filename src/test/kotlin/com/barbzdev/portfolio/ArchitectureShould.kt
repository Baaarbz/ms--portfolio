package com.barbzdev.portfolio

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test


const val DOMAIN_PACKAGE = "com.barbzdev.portfolio.domain.."
const val APPLICATION_PACKAGE = "com.barbzdev.portfolio.application.."
const val CONTROLLER_PACKAGE = "com.barbzdev.portfolio.infrastructure.framework.controller.."
const val JAVA_PACKAGE = "java.."
const val KOTLIN_PACKAGE = "kotlin.."
const val KOTLINX_PACKAGE = "kotlinx.."
const val LOGGER_PACKAGE = "org.slf4j.."


@AnalyzeClasses(packages = ["com.barbzdev.portfolio"], importOptions = [DoNotIncludeTests::class])
class ArchitectureShould : ArchitectureTest() {

  var ignoreTests = ImportOption { location ->
    !location.contains("/test/")
  }

  @Test
  fun `domain layer not depends of third party dependencies`() {
    val importedClasses = ClassFileImporter().importPackages("com.barbzdev.portfolio")

    val rule: ArchRule = classes()
      .that()
      .resideInAPackage(DOMAIN_PACKAGE)
      .should()
      .onlyAccessClassesThat()
      .resideInAnyPackage(DOMAIN_PACKAGE, JAVA_PACKAGE, KOTLINX_PACKAGE, KOTLIN_PACKAGE, LOGGER_PACKAGE)
      .because("Domain should not be coupled to any external dependency")

    rule.check(importedClasses)
  }

  @Test
  @Disabled
  fun `application layer only be accessed by controllers`() {
    val importedClasses = ClassFileImporter().importPackages("com.barbzdev.portfolio")

    val rule: ArchRule = classes()
      .that()
      .resideInAPackage(APPLICATION_PACKAGE)
      .should()
      .onlyBeAccessed()
      .byAnyPackage(CONTROLLER_PACKAGE)
      .because("Application layer should be only accessible by controllers")

    rule.check(importedClasses)
  }

  @Test
  @Disabled
  fun `application layer has only one public method execute()`() {
    val importedClasses = ClassFileImporter()
      .withImportOption(ignoreTests)
      .importPackages("com.barbzdev.portfolio")

    val rule: ArchRule = methods()
      .that()
      .areDeclaredInClassesThat()
      .resideInAPackage(APPLICATION_PACKAGE)
      .and()
      .arePublic()
      .should()
      .haveName("execute")
      .andShould()
      .bePublic()
      .because("Application layer should have only one public method execute per use case")

    rule.check(importedClasses)
  }

  @Test
  fun `domain layer not access to application layer`() {
    val importedClasses = ClassFileImporter().importPackages("com.barbzdev.portfolio")

    val rule: ArchRule = noClasses()
      .that()
      .resideInAPackage(DOMAIN_PACKAGE)
      .should()
      .accessClassesThat()
      .resideInAnyPackage(APPLICATION_PACKAGE)
      .because("We dont want to couple domain layer to application layer")

    rule.check(importedClasses)
  }
}
