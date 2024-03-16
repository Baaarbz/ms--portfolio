package com.barbzdev.portfolio.infrastructure

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.library.GeneralCodingRules
import org.slf4j.Logger

@Suppress("VariableNaming")
@AnalyzeClasses(packages = ["com.barbzdev.portfolio"], importOptions = [DoNotIncludeTests::class])
class HexagonalArchitectureShould {

  @ArchTest
  val `domain classes should not access infrastructure classes` = noClasses()
    .that()
    .resideInAPackage("..domain..")
    .should()
    .accessClassesThat()
    .resideInAnyPackage("..infrastructure..")

  @ArchTest
  val `domain classes should not access application classes` = noClasses()
    .that()
    .resideInAPackage("..domain..")
    .should()
    .accessClassesThat()
    .resideInAnyPackage("..application..")

  @ArchTest
  val `applicatio  classes should not access infrastructure classes` = noClasses()
    .that()
    .resideInAPackage("..application..")
    .should()
    .accessClassesThat()
    .resideInAnyPackage("..infrastructure..")
}
