package com.barbzdev.portfolio.usecase

import assertk.assertThat
import assertk.assertions.isBetween
import assertk.assertions.isEqualTo
import assertk.assertions.isEqualToIgnoringGivenProperties
import assertk.assertions.isNotNull
import com.barbzdev.portfolio.domain.Job
import com.barbzdev.portfolio.domain.JobData
import com.barbzdev.portfolio.domain.common.AuditableDate.Companion.of
import com.barbzdev.portfolio.domain.common.AuditableDateTime
import com.barbzdev.portfolio.domain.repository.JobRepository
import com.barbzdev.portfolio.domain.valueobject.CompanyName
import com.barbzdev.portfolio.domain.valueobject.CompanyUrl
import com.barbzdev.portfolio.domain.valueobject.Description
import com.barbzdev.portfolio.domain.valueobject.Id
import com.barbzdev.portfolio.domain.valueobject.JobEndDate
import com.barbzdev.portfolio.domain.valueobject.JobStartDate
import com.barbzdev.portfolio.domain.valueobject.JobUpdatedAt
import com.barbzdev.portfolio.domain.valueobject.Link
import com.barbzdev.portfolio.domain.valueobject.Role
import com.barbzdev.portfolio.infrastructure.AcceptanceTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

class CreateNewJobShould : AcceptanceTest() {

  @Value("\${com.barbzdev.auth.usr}")
  lateinit var user: String

  @Value("\${com.barbzdev.auth.pwd}")
  lateinit var password: String

  @Autowired
  private lateinit var jobRepository: JobRepository

  @Test
  fun `create a job and save it successfully`() {
    val response = executeCreateJobRequest()

    val jobId = response.extractJobIdFromLocationHeader()

    verifyJobIsSaved(jobId)
  }

  private fun verifyJobIsSaved(responseJobId: String) {
    val savedJob = jobRepository.findBy(Id(responseJobId))
    val expectedJob = buildExpectedJob(responseJobId)

    assertThat(savedJob)
      .isNotNull()
      .isEqualToIgnoringGivenProperties(expectedJob, Job::updatedAt, Job::startDate, Job::endDate)
    assertThat(savedJob!!.updatedAt.value.toOffsetDateTime()).isBetween(
      AuditableDateTime.now().toOffsetDateTime().minusSeconds(5),
      AuditableDateTime.now().toOffsetDateTime()
    )
    assertThat(savedJob.startDate.value.toLocalDate()).isEqualTo(of(EXPECTED_START_DATE).toLocalDate())
    assertThat(savedJob.endDate?.value?.toLocalDate()).isEqualTo(of(EXPECTED_END_DATE).toLocalDate())
  }

  private fun executeCreateJobRequest() = given()
    .port(port.toInt())
    .auth()
    .basic(user, password)
    .contentType(ContentType.JSON)
    .body(CREATE_JOB_REQUEST)
    .`when`()
    .post("/api/v1/jobs")
    .then()
    .assertThat()
    .statusCode(HttpStatus.CREATED.value())
    .extract()

  private fun ExtractableResponse<Response>.extractJobIdFromLocationHeader() =
    header(HttpHeaders.LOCATION)
      .split("/")
      .last()

  private fun buildExpectedJob(jobId: String): Job {
    val link = Link(
      name = EXPECTED_LINK_NAME,
      url = EXPECTED_LINK_URL
    )

    val expectedJobData = JobData(
      links = listOf(link),
      tags = listOf(EXPECTED_FIRST_TAG, EXPECTED_SECOND_TAG)
    )

    return Job(
      id = Id(jobId),
      companyName = CompanyName(EXPECTED_COMPANY_NAME),
      description = Description(EXPECTED_DESCRIPTION),
      role = Role(EXPECTED_ROLE),
      companyUrl = CompanyUrl(EXPECTED_COMPANY_URL),
      startDate = JobStartDate(of(EXPECTED_START_DATE)),
      endDate = JobEndDate(of(EXPECTED_END_DATE)),
      jobData = expectedJobData,
      updatedAt = JobUpdatedAt(AuditableDateTime.now())
    )
  }
}

private const val EXPECTED_COMPANY_NAME = "Banco Santander"
private const val EXPECTED_COMPANY_URL = "https://bancosantander.es/"
private const val EXPECTED_START_DATE = "30-10-2020"
private const val EXPECTED_END_DATE = "17-12-2023"
private const val EXPECTED_ROLE = "Backend Engineer"
private const val EXPECTED_DESCRIPTION = "Develop the most important project of Banco Santander, migrating from Cobol to Scrath"
private const val EXPECTED_LINK_NAME = "Santander Investor"
private const val EXPECTED_LINK_URL = "https://www.santander.com/en/shareholders-and-investors"
private const val EXPECTED_FIRST_TAG = "Java"
private const val EXPECTED_SECOND_TAG = "Spring Boot"

@Language("JSON")
private const val CREATE_JOB_REQUEST = """
 {
    "companyName":"$EXPECTED_COMPANY_NAME",
    "companyUrl":"$EXPECTED_COMPANY_URL",
    "role":"$EXPECTED_ROLE",
    "description":"$EXPECTED_DESCRIPTION",
    "startDate":"$EXPECTED_START_DATE",
    "endDate":"$EXPECTED_END_DATE",
    "jobData":{
       "links": [
          {
            "name": "$EXPECTED_LINK_NAME",
            "url": "$EXPECTED_LINK_URL"
          }
       ],
       "tags": [
          "$EXPECTED_FIRST_TAG",
          "$EXPECTED_SECOND_TAG"
       ]
    }
 }
  """
