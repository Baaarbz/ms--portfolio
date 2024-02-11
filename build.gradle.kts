import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id 'org.springframework.boot' version '3.1.5'
  id 'io.spring.dependency-management' version '1.1.3'
  id 'org.jetbrains.kotlin.jvm' version '1.9.20'
  id 'org.jetbrains.kotlin.plugin.spring' version '1.9.20'
}

apply plugin: 'java-library'
apply plugin: 'java-test-fixtures'

group = 'com.barbzdev'
version = '0.0.1-SNAPSHOT'

java {
  sourceCompatibility = '17'
}

repositories {
  mavenCentral()
}

dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-actuator'
  implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
  implementation 'org.springframework.boot:spring-boot-starter-security'
  implementation 'org.springframework.boot:spring-boot-starter-web'
  implementation 'com.fasterxml.jackson.module:jackson-module-kotlin'
  implementation 'org.flywaydb:flyway-core'
  implementation 'org.jetbrains.kotlin:kotlin-reflect'
  implementation 'com.google.code.gson:gson:2.10.1'
  implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0'

  developmentOnly 'org.springframework.boot:spring-boot-devtools'

  runtimeOnly 'org.postgresql:postgresql'

  testImplementation 'org.springframework.boot:spring-boot-starter-test'
  testImplementation 'org.springframework.boot:spring-boot-testcontainers'
  testImplementation 'org.springframework.security:spring-security-test'
  testImplementation 'org.testcontainers:junit-jupiter'
  testImplementation 'org.testcontainers:postgresql'
  testImplementation 'io.rest-assured:rest-assured:5.3.2'
  testImplementation 'io.mockk:mockk:1.13.8'
  testImplementation 'org.skyscreamer:jsonassert:1.5.1'
  testImplementation 'com.tngtech.archunit:archunit-junit5:1.2.0'

  testFixturesImplementation 'com.github.javafaker:javafaker:1.0.2'
}

tasks.withType(KotlinCompile) {
  kotlinOptions {
    freeCompilerArgs += "-Xjsr305=strict"
    jvmTarget = "17"
  }
}

tasks.register('integrationTest', Test) {
  description = "Runs integration tests."
  group = "verification"
}

tasks.register('acceptanceTest', Test) {
  description = "Runs acceptance tests."
  group = "verification"
}

tasks.register('architectureTest', Test) {
  description = "Runs architecture tests."
  group = "verification"
}

tasks.test {
  useJUnitPlatform {
    excludeTags("IntegrationTest")
    excludeTags("AcceptanceTest")
    excludeTags("ArchitectureTest")
    includeTags("UnitTest")
  }
}

tasks.acceptanceTest {
  useJUnitPlatform {
    excludeTags("IntegrationTest")
    includeTags("AcceptanceTest")
    excludeTags("ArchitectureTest")
    excludeTags("UnitTest")
  }
}

tasks.integrationTest {
  useJUnitPlatform {
    includeTags("IntegrationTest")
    excludeTags("AcceptanceTest")
    excludeTags("ArchitectureTest")
    excludeTags("UnitTest")
  }
}

tasks.architectureTest {
  useJUnitPlatform()
  useJUnitPlatform {
    excludeTags("IntegrationTest")
    excludeTags("AcceptanceTest")
    includeTags("ArchitectureTest")
    excludeTags("UnitTest")
  }
}

tasks.check {
  dependsOn integrationTest
  dependsOn acceptanceTest
}
