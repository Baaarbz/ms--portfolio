package com.barbzdev.portfolio.application

import com.barbzdev.portfolio.domain.job.Job
import com.barbzdev.portfolio.domain.job.JobData
import com.barbzdev.portfolio.domain.job.JobRepository
import com.barbzdev.portfolio.infrastructure.framework.controller.HttpGetJobsResponse
import com.barbzdev.portfolio.infrastructure.framework.controller.JobDataResponse

class GetAllJobsService(
  private val jobs: JobRepository
) {

  fun execute(): List<HttpGetJobsResponse> = jobs.findAll()
    .sortedByDescending { it.joinStartDateAsNumber() }
    .map { it.toHttpGetAllResponse() }

  private fun Job.toHttpGetAllResponse() = HttpGetJobsResponse(
    id = this.id.value,
    companyName = this.companyName.value,
    companyURL = this.companyURL.value,
    isCurrentCompany = this.isCurrentCompany,
    companyStartMonth = this.companyStartMonth.value,
    companyStartYear = this.companyStartYear.value,
    companyEndMonth = this.companyEndMonth?.value,
    companyEndYear = this.companyEndYear?.value,
    jobData = this.jobData.toHttpGetAllResponse()
  )

  private fun JobData.toHttpGetAllResponse() = JobDataResponse(
    positions = this.positions.map { it.toHttpGetAllResponse() }
  )

  private fun JobData.Position.toHttpGetAllResponse() = JobDataResponse.PositionResponse(
    position = this.position.value,
    description = this.description.value,
    isCurrentPosition = this.isCurrentPosition,
    positionStartMonth = this.positionStartMonth.value,
    positionStartYear = this.positionStartYear.value,
    positionEndMonth = this.positionEndMonth?.value,
    positionEndYear = this.positionEndYear?.value,
  )
}
