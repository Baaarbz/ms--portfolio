package dev.eduardobarbosa.portfolio.infrastructure.controller

import dev.eduardobarbosa.portfolio.AcceptanceTest
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.http.HttpStatus

class AboutControllerShould : AcceptanceTest() {
  @Test
  fun `return last about registered on db`() {
    val responseJobs = RestAssured.given()
      .port(port.toInt())
      .contentType(ContentType.JSON)
      .`when`()
      .get("/api/v1/abouts")
      .then()
      .assertThat()
      .statusCode(HttpStatus.OK.value())
      .extract()
      .body()
      .asString()

    JSONAssert.assertEquals(GET_ABOUT_RESPONSE, responseJobs, JSONCompareMode.NON_EXTENSIBLE)
  }
}

@Language("JSON")
private const val GET_ABOUT_RESPONSE = """
{
  "id":"4db5f43c-d241-4b1d-b769-351d09e3447e",
  "about":"last inserted about"
}
  """
