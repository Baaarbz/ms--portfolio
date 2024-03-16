package com.barbzdev.portfolio.usecase

import com.barbzdev.portfolio.infrastructure.AcceptanceTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.http.HttpStatus

class GetAllJobsShould : AcceptanceTest() {

  @Test
  fun `return list of all jobs`() {
    val response = executeGetAllJobsRequest()

    JSONAssert.assertEquals(GET_JOBS_RESPONSE, response, JSONCompareMode.NON_EXTENSIBLE)
  }

  private fun executeGetAllJobsRequest() = given()
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
}

@Language("JSON")
private const val GET_JOBS_RESPONSE = """
[
   {
      "id":"7cd9a4dc-ff61-48da-af2c-c839d6572b3a",
      "companyName":"Adevinta",
      "role":"Backend Engineer",
      "description":"Collaborate developing different solutions in different marketplaces like: Milanuncios, Leboncoin, Kleinanzeigen, mobile.de... using Kotlin, Go and Java (17). Use different technologies as Kafka, AWS, Datadog (metrics, alerts, monitoring), Optimizely (for feature flagging, test A/B), Segment (to monitor user behaviors), Docker and Kubernetes. I am used to give light talks to share knowledge to other mates about new tech, or solutions adopted in our team. Design patterns like DDD, Hexagonal architecture, good practices as TDD, SOLID, pair programming, and very used to work using async flows. Create documentation",
      "companyUrl":"https://adevinta.com/",
      "startDate":"10-01-2022",
      "endDate":null,
      "isCurrentCompany":true,
      "jobData":{
         "links":null,
         "tags": [
            "AWS",
            "Java"
         ]
      }
   },
   {
      "id":"86c144f4-0e1d-408b-b274-106ef8939b4b",
      "companyName":"adidas",
      "role":"Software Developer",
      "description":"Work in a international team developing components in AWS and APIs with Spring Boot, research new solutions (PoC) to improve performance and use new AWS tools, also use terraform to automatize the deployment of the components in AWS. Monitoring (logs, metrics and traces) with Lenses (for Kafka), AWS Cloudwatch, Instana, Grafana... between others. Create/update documentation in Confluence.",
      "companyUrl":"https://adidas.com/",
      "startDate":"04-01-2022",
      "endDate":"10-01-2022",
      "isCurrentCompany":false,
      "jobData":{
         "links":[
            {
               "name":"Adidas wallet",
               "url":"https://adidas.com/wallet"
            }
         ],
         "tags": [
            "AWS",
            "Kotlin"
         ]
      }
   }
]
  """
