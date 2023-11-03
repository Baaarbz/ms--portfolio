package com.barbzdev.portfolio.application

import com.barbzdev.portfolio.domain.common.Month
import com.barbzdev.portfolio.domain.common.Year
import com.barbzdev.portfolio.domain.job.Job
import com.barbzdev.portfolio.domain.job.JobData
import com.barbzdev.portfolio.domain.job.JobRepository
import com.barbzdev.portfolio.infrastructure.framework.controller.HttpPostNewJobRequest
import com.barbzdev.portfolio.infrastructure.framework.controller.JobDataRequest
import java.util.UUID

class CreateNewJobService(
  private val jobs: JobRepository
) {

  fun execute(newJobRequest: HttpPostNewJobRequest): String {
    val domainJob = newJobRequest.toDomain()
    jobs.save(domainJob)
    return domainJob.id.value
  }

  private fun HttpPostNewJobRequest.toDomain() = Job(
    id = Job.Id(UUID.randomUUID().toString()),
    companyName = Job.CompanyName(this.companyName),
    companyURL = Job.CompanyURL(this.companyURL),
    isCurrentCompany = this.isCurrentCompany,
    companyStartMonth = Month(this.companyStartMonth),
    companyStartYear = Year(this.companyStartYear),
    companyEndMonth = this.companyEndMonth?.let { Month(it) },
    companyEndYear = this.companyEndYear?.let { Year(it) },
    jobData = this.jobData.toDomain()
  )

  private fun JobDataRequest.toDomain() = JobData(
    positions = this.positions.map { it.toDomain() }
  )

  private fun JobDataRequest.PositionRequest.toDomain() = JobData.Position(
    position = JobData.PositionName(this.position),
    description = JobData.Description(this.description),
    isCurrentPosition = this.isCurrentPosition,
    positionStartMonth = Month(this.positionStartMonth),
    positionStartYear = Year(this.positionStartYear),
    positionEndMonth = this.positionEndMonth?.let { Month(it) },
    positionEndYear = this.positionEndYear?.let { Year(it) },
  )
}
