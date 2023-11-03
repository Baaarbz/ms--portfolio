package com.barbzdev.portfolio

import com.barbzdev.portfolio.domain.common.Month
import com.barbzdev.portfolio.domain.common.Year
import com.barbzdev.portfolio.domain.job.Job
import com.barbzdev.portfolio.domain.job.JobData
import com.barbzdev.portfolio.infrastructure.framework.controller.HttpPostNewJobRequest
import com.barbzdev.portfolio.infrastructure.framework.controller.JobDataRequest
import com.github.javafaker.Faker
import java.util.UUID

object JobFactory {

  private val faker = Faker.instance()
  fun aJob(): Job {
    val fakerCompany = faker.company();

    return Job(
      id = Job.Id(UUID.randomUUID().toString()),
      companyName = Job.CompanyName(fakerCompany.name()),
      companyURL = Job.CompanyURL(fakerCompany.url()),
      companyStartMonth = Month(faker.number().numberBetween(10, 12).toString()),
      companyStartYear = Year(faker.number().numberBetween(1990, 2040).toString()),
      companyEndMonth = null,
      companyEndYear = null,
      isCurrentCompany = true,
      jobData = aJobData()
    )
  }

  private fun aJobData(): JobData {
    val fakerJob = faker.job()

    return JobData(
      positions = listOf(
        JobData.Position(
          position = JobData.PositionName(fakerJob.title()),
          description = JobData.Description(fakerJob.position()),
          isCurrentPosition = true,
          positionStartMonth = Month("06"),
          positionStartYear = Year("2022"),
          positionEndMonth = null,
          positionEndYear = null,
        )
      )
    )
  }

  fun aJobCreateRequest(): HttpPostNewJobRequest {
    val fakerCompany = faker.company();

    return HttpPostNewJobRequest(
      companyName = fakerCompany.name(),
      companyURL = fakerCompany.url(),
      companyStartMonth = faker.number().numberBetween(10, 12).toString(),
      companyStartYear = faker.number().numberBetween(1990, 2040).toString(),
      companyEndMonth = null,
      companyEndYear = null,
      isCurrentCompany = true,
      jobData = aJobDataCreateRequest()
    )
  }

  private fun aJobDataCreateRequest(): JobDataRequest {
    val fakerJob = faker.job()

    return JobDataRequest(
      positions = listOf(
        JobDataRequest.PositionRequest(
          position = fakerJob.title(),
          description = fakerJob.position(),
          isCurrentPosition = true,
          positionStartMonth = "06",
          positionStartYear = "2022",
          positionEndMonth = null,
          positionEndYear = null,
        )
      )
    )
  }
}
