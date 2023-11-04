package com.barbzdev.portfolio

import com.barbzdev.portfolio.infrastructure.framework.PortfolioApplication
import io.mockk.junit5.MockKExtension
import java.io.File
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest(
  classes = [PortfolioApplication::class],
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
sealed class ConfigurationTestEnvironment {

  @LocalServerPort
  lateinit var port: String

  @Autowired
  private lateinit var flyway: Flyway

  @BeforeEach
  fun setUp() {
    flyway.clean()
    flyway.migrate()
  }

  companion object {
    private const val DOCKER_COMPOSE_PATH = "docker-compose.yml"
    private val dockerComposeContainer: DockerComposeContainer<*> by lazy {
      DockerComposeContainer<Nothing>(File(DOCKER_COMPOSE_PATH))
    }

    init {
      dockerComposeContainer.start()
      dockerComposeContainer
        .withLocalCompose(true)
        .waitingFor("postgres", Wait.forListeningPort())
      Runtime.getRuntime().addShutdownHook(Thread { dockerComposeContainer.stop() })
    }
  }
}

@Tag("UnitTest")
@ExtendWith(MockKExtension::class)
abstract class UnitTest

@Tag("AcceptanceTest")
abstract class AcceptanceTest : ConfigurationTestEnvironment()

@Tag("IntegrationTest")
abstract class IntegrationTest : ConfigurationTestEnvironment()
