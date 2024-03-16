package com.barbzdev.portfolio.infrastructure

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields
import com.tngtech.archunit.library.GeneralCodingRules
import org.slf4j.Logger

@Suppress("VariableNaming")
@AnalyzeClasses(packages = ["com.barbzdev.portfolio"], importOptions = [DoNotIncludeTests::class])
class GeneralCodingRulesArchitectureShould {
  @ArchTest
  val `no classes should access standard streams` = GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS

  @ArchTest
  val `no classes should throw generic exceptions` = GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS

  @ArchTest
  val `no classes should use java util` = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING

  @ArchTest
  val `no classes should use jodatime` = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME

  @ArchTest
  val `no classes should use field injections` = GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION
}
