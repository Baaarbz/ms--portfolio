package com.barbzdev.portfolio

import com.barbzdev.portfolio.application.CreateNewJobRequest
import com.barbzdev.portfolio.application.JobDataRequest
import com.barbzdev.portfolio.application.LinkRequest
import com.barbzdev.portfolio.application.RoleRequest
import com.barbzdev.portfolio.domain.Job
import com.barbzdev.portfolio.domain.JobData
import com.barbzdev.portfolio.domain.common.AuditableDate
import com.barbzdev.portfolio.domain.common.AuditableDateTime
import com.barbzdev.portfolio.domain.valueobject.CompanyName
import com.barbzdev.portfolio.domain.valueobject.CompanyUrl
import com.barbzdev.portfolio.domain.valueobject.Id
import com.barbzdev.portfolio.domain.valueobject.JobStartDate
import com.barbzdev.portfolio.domain.valueobject.JobUpdatedAt
import com.barbzdev.portfolio.domain.valueobject.Link
import com.barbzdev.portfolio.domain.valueobject.Role
import com.barbzdev.portfolio.domain.valueobject.RoleDescription
import com.barbzdev.portfolio.domain.valueobject.RoleName
import com.barbzdev.portfolio.domain.valueobject.RoleStartDate
import com.barbzdev.portfolio.domain.valueobject.Tag
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
      roles = listOf(
        Role(
          name = RoleName(fakerJob.title()),
          description = RoleDescription(fakerJob.position()),
          startDate = RoleStartDate(
            AuditableDate.of(
              LocalDate.of(
                faker.number().numberBetween(1990, 2023),
                faker.number().numberBetween(1, 12),
                faker.number().numberBetween(1, 28),
              ).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            )
          ),
          endDate = null,
        )
      ),
      links = listOf(
        Link(
          name = fakerJob.keySkills(),
          url = "https://www.barbzdev.com"
        )
      ),
      tags = listOf(Tag(fakerJob.keySkills()))
    )
  }

  fun aJobCreateRequest(): CreateNewJobRequest {
    val fakerCompany = faker.company();

    return CreateNewJobRequest(
      companyName = fakerCompany.name(),
      companyUrl = fakerCompany.url(), startDate =
      "${faker.number().numberBetween(1, 28)}-${faker.number().numberBetween(1, 12)}-${faker.number().numberBetween(1990, 2023)}",
      endDate = null,
      jobData = aJobDataCreateRequest()
    )
  }

  private fun aJobDataCreateRequest(): JobDataRequest {
    val fakerJob = faker.job()

    return JobDataRequest(
      roles = listOf(
        RoleRequest(
          role = fakerJob.title(),
          description = fakerJob.position(),
          startDate = "${faker.number().numberBetween(1, 28)}-${faker.number().numberBetween(1, 12)}-${
            faker.number().numberBetween(1990, 2023)
          }",
          endDate = null,
        )
      ),
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
