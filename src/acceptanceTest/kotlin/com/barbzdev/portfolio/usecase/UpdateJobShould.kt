package com.barbzdev.portfolio.usecase

import assertk.assertThat
import assertk.assertions.isBetween
import assertk.assertions.isEqualToIgnoringGivenProperties
import assertk.assertions.isNotNull
import com.barbzdev.portfolio.infrastructure.AcceptanceTest
import com.barbzdev.portfolio.domain.common.AuditableDate
import com.barbzdev.portfolio.domain.common.AuditableDateTime
import com.barbzdev.portfolio.domain.valueobject.CompanyName
import com.barbzdev.portfolio.domain.valueobject.CompanyUrl
import com.barbzdev.portfolio.domain.valueobject.Id
import com.barbzdev.portfolio.domain.Job
import com.barbzdev.portfolio.domain.JobData
import com.barbzdev.portfolio.domain.valueobject.JobEndDate
import com.barbzdev.portfolio.domain.valueobject.JobStartDate
import com.barbzdev.portfolio.domain.valueobject.JobUpdatedAt
import com.barbzdev.portfolio.domain.valueobject.Link
import com.barbzdev.portfolio.domain.repository.JobRepository
import com.barbzdev.portfolio.domain.valueobject.Description
import com.barbzdev.portfolio.domain.valueobject.Role
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import java.time.OffsetDateTime
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus

class UpdateJobShould : AcceptanceTest() {

  @Value("\${com.barbzdev.auth.usr}")
  lateinit var user: String

  @Value("\${com.barbzdev.auth.pwd}")
  lateinit var password: String

  @Autowired
  private lateinit var jobRepository: JobRepository

  @Test
  @Disabled
  fun `update job giving its id`() {
    executeUpdateJobRequest()

    val savedJob = jobRepository.findBy(Id(JOB_ID))
    val expectedJob = buildExpectUpdatedJob()
    assertThat(savedJob)
      .isNotNull()
      .isEqualToIgnoringGivenProperties(expectedJob, Job::updatedAt)
    assertThat(savedJob!!.updatedAt.value.toOffsetDateTime())
      .isBetween(OffsetDateTime.now(), OffsetDateTime.now().minusSeconds(5))
  }

  private fun buildExpectUpdatedJob(): Job {
    val link = Link(
      name = EXPECTED_LINK_NAME,
      url = EXPECTED_LINK_URL
    )

    val expectedJobData = JobData(
      links = listOf(link),
      tags = listOf(EXPECTED_FIRST_TAG)
    )

    return Job(
      id = Id(JOB_ID),
      companyName = CompanyName(EXPECTED_COMPANY_NAME),
      role = Role(EXPECTED_ROLE),
      description = Description(EXPECTED_DESCRIPTION),
      companyUrl = CompanyUrl(EXPECTED_COMPANY_URL),
      startDate = JobStartDate(AuditableDate.of(EXPECTED_START_DATE)),
      endDate = JobEndDate(AuditableDate.of(EXPECTED_END_DATE)),
      jobData = expectedJobData,
      updatedAt = JobUpdatedAt(AuditableDateTime.now())
    )
  }

  private fun executeUpdateJobRequest() {
    given()
      .port(port.toInt())
      .auth()
      .basic(user, password)
      .contentType(ContentType.JSON)
      .body(UPDATE_JOB_REQUEST)
      .`when`()
      .put("/api/v1/jobs/$JOB_ID")
      .then()
      .assertThat()
      .statusCode(HttpStatus.OK.value())
  }
}

private const val JOB_ID = "7cd9a4dc-ff61-48da-af2c-c839d6572b3a"

private const val EXPECTED_COMPANY_NAME = "Banco Santander"
private const val EXPECTED_COMPANY_URL = "https://bancosantander.es/"
private const val EXPECTED_START_DATE = "30-10-2020"
private const val EXPECTED_END_DATE = "17-12-2023"
private const val EXPECTED_ROLE = "Backend Engineer"
private const val EXPECTED_DESCRIPTION = "Develop the most important project of Banco Santander, migrating from Cobol to Scrath"
private const val EXPECTED_LINK_NAME = "Santander Investor"
private const val EXPECTED_LINK_URL = "https://www.santander.com/en/shareholders-and-investors"
private const val EXPECTED_FIRST_TAG = "Go"

@Language("JSON")
private const val UPDATE_JOB_REQUEST = """
 {
    "companyName":"$EXPECTED_COMPANY_NAME",
    "companyURL":"$EXPECTED_COMPANY_URL",
    "startDate":"$EXPECTED_START_DATE",
    "endDate":"$EXPECTED_END_DATE",
    "role":"$EXPECTED_ROLE",
    "description":"$EXPECTED_DESCRIPTION",
    "jobData":{
       "links": [
          {
            "name": "$EXPECTED_LINK_NAME",
            "url": "$EXPECTED_LINK_URL"
          }
       ],
       "tags": [
          "$EXPECTED_FIRST_TAG"
       ]
    }
 }
  """
