package com.barbzdev

import com.barbzdev.portfolio.infrastructure.framework.PortfolioApplication
import java.io.File
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait

@ActiveProfiles("integration-test")
@SpringBootTest(
  classes = [PortfolioApplication::class],
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
abstract class IntegrationTest {

  @Autowired
  private lateinit var flyway: Flyway

  @BeforeEach
  fun beforeEach() {
    flyway.clean()
    flyway.migrate()
  }

  companion object {
    private const val DOCKER_COMPOSE_PATH = "docker-compose.yml"
    private val dockerComposeContainer = DockerComposeContainer(File(DOCKER_COMPOSE_PATH))
      .withLocalCompose(true)
      .waitingFor("postgres", Wait.forListeningPort())

    init {
      dockerComposeContainer.start()
      Runtime.getRuntime().addShutdownHook(Thread { dockerComposeContainer.stop() })
    }
  }
}
