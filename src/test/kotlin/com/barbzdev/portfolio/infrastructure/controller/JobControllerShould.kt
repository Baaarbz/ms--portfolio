package com.barbzdev.portfolio.infrastructure.controller

import com.barbzdev.portfolio.AcceptanceTest
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.http.HttpStatus

class JobControllerShould : AcceptanceTest() {

    @Test
    fun `return jobs ordered by dates given get request without auth`() {
        val responseJobs = RestAssured.given()
                .port(port.toInt())
                .contentType(ContentType.JSON)
                .`when`()
                .get("/api/v1/jobs")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .asString()

        JSONAssert.assertEquals(GET_JOBS_RESPONSE, responseJobs, JSONCompareMode.NON_EXTENSIBLE)
    }

    @Test
    fun `return unauthorized response given create job request without auth`() {
        RestAssured.given()
                .port(port.toInt())
                .contentType(ContentType.JSON)
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
                .contentType(ContentType.JSON)
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
                .contentType(ContentType.JSON)
                .`when`()
                .delete("/api/v1/jobs/7cd9a4dc-ff61-48da-af2c-c839d6572b3a")
                .then()
                .assertThat()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
    }
}

@Language("JSON")
private const val GET_JOBS_RESPONSE = """
[
   {
      "id":"7cd9a4dc-ff61-48da-af2c-c839d6572b3a",
      "companyName":"Adevinta",
      "companyURL":"https://adevinta.com/",
      "isCurrentCompany":true,
      "companyStartMonth":"10",
      "companyStartYear":"2022",
      "companyEndMonth":null,
      "companyEndYear":null,
      "jobData":{
         "positions":[
            {
               "position":"Backend Engineer",
               "description":"Collaborate developing different solutions in different marketplaces like: Milanuncios, Leboncoin, Kleinanzeigen, mobile.de... using Kotlin, Go and Java (17). Use different technologies as Kafka, AWS, Datadog (metrics, alerts, monitoring), Optimizely (for feature flagging, test A/B), Segment (to monitor user behaviors), Docker and Kubernetes. I am used to give light talks to share knowledge to other mates about new tech, or solutions adopted in our team. Design patterns like DDD, Hexagonal architecture, good practices as TDD, SOLID, pair programming, and very used to work using async flows. Create documentation",
               "isCurrentPosition":true,
               "positionStartMonth":"10",
               "positionStartYear":"2022",
               "positionEndMonth":null,
               "positionEndYear":null
            }
         ]
      }
   },
   {
      "id":"86c144f4-0e1d-408b-b274-106ef8939b4b",
      "companyName":"adidas",
      "companyURL":"https://adidas.com/",
      "isCurrentCompany":false,
      "companyStartMonth":"04",
      "companyStartYear":"2022",
      "companyEndMonth":"10",
      "companyEndYear":"2022",
      "jobData":{
         "positions":[
            {
               "position":"Software Developer",
               "description":"Work in a international team developing components in AWS and APIs with Spring Boot, research new solutions (PoC) to improve performance and use new AWS tools, also use terraform to automatize the deployment of the components in AWS. Monitoring (logs, metrics and traces) with Lenses (for Kafka), AWS Cloudwatch, Instana, Grafana... between others. Create/update documentation in Confluence.",
               "isCurrentPosition":false,
               "positionStartMonth":"04",
               "positionStartYear":"2022",
               "positionEndMonth":"10",
               "positionEndYear":"2022"
            }
         ]
      }
   }
]
  """