package com.barbzdev.portfolio

import com.barbzdev.portfolio.application.CreateNewJobRequest
import com.barbzdev.portfolio.application.JobDataRequest
import com.barbzdev.portfolio.application.LinkRequest
import com.barbzdev.portfolio.domain.Job
import com.barbzdev.portfolio.domain.JobData
import com.barbzdev.portfolio.domain.common.AuditableDate
import com.barbzdev.portfolio.domain.common.AuditableDateTime
import com.barbzdev.portfolio.domain.valueobject.CompanyName
import com.barbzdev.portfolio.domain.valueobject.CompanyUrl
import com.barbzdev.portfolio.domain.valueobject.Description
import com.barbzdev.portfolio.domain.valueobject.Id
import com.barbzdev.portfolio.domain.valueobject.JobStartDate
import com.barbzdev.portfolio.domain.valueobject.JobUpdatedAt
import com.barbzdev.portfolio.domain.valueobject.Link
import com.barbzdev.portfolio.domain.valueobject.Role
import com.github.javafaker.Faker
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

object JobFactory {

  private val faker = Faker.instance()
  fun aJob(): Job {
    val fakerCompany = faker.company();

    return Job(
      id = Id(UUID.randomUUID().toString()),
      companyName = CompanyName(fakerCompany.name()),
      description = Description(fakerCompany.bs()),
      role = Role(fakerCompany.profession()),
      companyUrl = CompanyUrl(fakerCompany.url()),
      startDate = JobStartDate(
        AuditableDate.of(
          LocalDate.of(
            faker.number().numberBetween(1990, 2023),
            faker.number().numberBetween(1, 12),
            faker.number().numberBetween(1, 28),
          ).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        )
      ),
      endDate = null,
      jobData = aJobData(),
      updatedAt = JobUpdatedAt(AuditableDateTime.now())
    )
  }

  private fun aJobData(): JobData {
    val fakerJob = faker.job()

    return JobData(
      links = listOf(
        Link(
          name = fakerJob.keySkills(),
          url = "https://www.barbzdev.com"
        )
      ),
      tags = listOf(fakerJob.keySkills())
    )
  }

  fun aJobCreateRequest(): CreateNewJobRequest {
    val fakerCompany = faker.company();

    return CreateNewJobRequest(
      companyName = fakerCompany.name(),
      description = fakerCompany.bs(),
      role = fakerCompany.profession(),
      companyUrl = fakerCompany.url(), startDate =
      "28-01-2023",
      endDate = null,
      jobData = aJobDataCreateRequest()
    )
  }

  private fun aJobDataCreateRequest(): JobDataRequest {
    val fakerJob = faker.job()

    return JobDataRequest(
      links = listOf(
        LinkRequest(
          name = fakerJob.keySkills(),
          url = "https://www.barbzdev.com"
        )
      ),
      tags = listOf(fakerJob.keySkills()),
    )
  }
}
