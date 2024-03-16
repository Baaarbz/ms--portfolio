package com.barbzdev.portfolio.usecase

import assertk.assertThat
import assertk.assertions.isNull
import com.barbzdev.portfolio.infrastructure.AcceptanceTest
import com.barbzdev.portfolio.domain.valueobject.Id
import com.barbzdev.portfolio.domain.repository.JobRepository
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus

class DeleteJobShould : AcceptanceTest() {

  @Value("\${com.barbzdev.auth.usr}")
  lateinit var user: String

  @Value("\${com.barbzdev.auth.pwd}")
  lateinit var password: String

  @Autowired
  private lateinit var jobRepository: JobRepository

  @Test
  @Disabled
  fun `delete job by its id successfully`() {
    executeDeleteRequest()

    val deletedJob = jobRepository.findBy(Id(JOB_ID))
    assertThat(deletedJob).isNull()
  }

  private fun executeDeleteRequest() {
    given()
      .port(port.toInt())
      .auth()
      .basic(user, password)
      .`when`()
      .delete("/api/v1/jobs/$JOB_ID")
      .then()
      .assertThat()
      .statusCode(HttpStatus.OK.value())
  }
}

private const val JOB_ID = "7cd9a4dc-ff61-48da-af2c-c839d6572b3a"
