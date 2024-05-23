import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  val kotlinVersion = "1.9.23"
  kotlin("jvm") version kotlinVersion
  kotlin("plugin.spring") version kotlinVersion
  kotlin("plugin.jpa") version kotlinVersion

  id("org.springframework.boot") version "3.1.5"
  id("io.spring.dependency-management") version "1.1.4"

  `java-test-fixtures`
}

group = "com.barbzdev"
version = "1.0.0"


java {
  sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.data:spring-data-jpa")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.flywaydb:flyway-core")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0")

  developmentOnly("org.springframework.boot:spring-boot-devtools")
  developmentOnly("org.springframework.boot:spring-boot-docker-compose")

  runtimeOnly("org.postgresql:postgresql")

  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

  testFixturesImplementation("com.github.javafaker:javafaker:1.0.2")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs += "-Xjsr305=strict"
    jvmTarget = "17"
  }
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      useJUnitJupiter()

      dependencies {
        implementation("com.willowtreeapps.assertk:assertk:0.28.0")
        implementation("com.tngtech.archunit:archunit-junit5:1.2.1")
        implementation("io.mockk:mockk:1.13.9")
      }
    }

    withType(JvmTestSuite::class).matching { it.name in listOf("integrationTest", "acceptanceTest") }.configureEach {
      useJUnitJupiter()
      dependencies {
        implementation(project())
        implementation("com.willowtreeapps.assertk:assertk:0.28.0")
        implementation("org.testcontainers:postgresql")
        implementation("org.springframework.boot:spring-boot-testcontainers")
        implementation("org.springframework.boot:spring-boot-starter-test")
        implementation("org.flywaydb:flyway-core")
        implementation(testFixtures(project()))
      }
    }

    val integrationTest by registering(JvmTestSuite::class) {
      targets {
        all {
          testTask.configure {
            shouldRunAfter(test)
          }
        }
      }
    }

    val acceptanceTest by registering(JvmTestSuite::class) {
      dependencies {
        implementation("org.springframework:spring-web:6.1.8")
        implementation("io.rest-assured:rest-assured:5.4.0")
        implementation("org.skyscreamer:jsonassert:1.5.1")
      }

      targets {
        all {
          testTask.configure {
            shouldRunAfter(integrationTest)
          }
        }
      }
    }
  }
}

tasks.named("check") {
  dependsOn(testing.suites.named("integrationTest"), testing.suites.named("acceptanceTest"))
}
