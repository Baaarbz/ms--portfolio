package com.barbzdev.portfolio.usecase

import com.barbzdev.portfolio.infrastructure.AcceptanceTest
import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class SecurityShould : AcceptanceTest() {

  @Test
  fun `return unauthorized response given create job request without auth`() {
    RestAssured.given()
      .port(port.toInt())
      .`when`()
      .post("/api/v1/jobs")
      .then()
      .assertThat()
      .statusCode(HttpStatus.UNAUTHORIZED.value())
  }

  @Test
  fun `return unauthorized response given update job request without auth`() {
    RestAssured.given()
      .port(port.toInt())
      .`when`()
      .put("/api/v1/jobs/7cd9a4dc-ff61-48da-af2c-c839d6572b3a")
      .then()
      .assertThat()
      .statusCode(HttpStatus.UNAUTHORIZED.value())
  }

  @Test
  fun `return unauthorized response given delete job request without auth`() {
    RestAssured.given()
      .port(port.toInt())
      .`when`()
      .delete("/api/v1/jobs/7cd9a4dc-ff61-48da-af2c-c839d6572b3a")
      .then()
      .assertThat()
      .statusCode(HttpStatus.UNAUTHORIZED.value())
  }
}
