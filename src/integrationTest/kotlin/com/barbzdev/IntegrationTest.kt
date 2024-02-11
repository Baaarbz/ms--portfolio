package com.barbzdev

import com.barbzdev.portfolio.infrastructure.framework.PortfolioApplication
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@SpringBootTest(
  classes = [TestApplication::class],
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  properties = ["spring.profiles.active=integration-test"]
)
abstract class IntegrationTest {

  @Autowired
  private lateinit var flyway: Flyway

  @BeforeEach
  fun beforeEach() {
    flyway.clean()
    flyway.migrate()
  }
}

fun main(args: Array<String>) {
  fromApplication<PortfolioApplication>().with(TestApplication::class).run(*args)
}

@TestConfiguration(proxyBeanMethods = false)
class TestApplication {

  @Bean
  @ServiceConnection
  fun postgresContainer(): PostgreSQLContainer<*> {
    return PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
  }
}
