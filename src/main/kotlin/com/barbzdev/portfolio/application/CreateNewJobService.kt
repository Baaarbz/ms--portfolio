package com.barbzdev.portfolio.application

import com.barbzdev.portfolio.domain.common.Month
import com.barbzdev.portfolio.domain.common.Year
import com.barbzdev.portfolio.domain.job.Job
import com.barbzdev.portfolio.domain.job.JobData
import com.barbzdev.portfolio.domain.job.JobRepository
import com.fasterxml.jackson.annotation.JsonProperty
import java.net.URI
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
    positions = this.positions.map { it.toDomain() },
    links = this.links?.map { JobData.Link(name = JobData.LinkName(it.name), url = JobData.LinkURL(it.url)) }
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


data class HttpPostNewJobRequest(
  @JsonProperty("companyName") val companyName: String,
  @JsonProperty("companyURL") val companyURL: String,
  @JsonProperty("isCurrentCompany") val isCurrentCompany: Boolean,
  @JsonProperty("companyStartMonth") val companyStartMonth: String,
  @JsonProperty("companyStartYear") val companyStartYear: String,
  @JsonProperty("companyEndMonth") val companyEndMonth: String?,
  @JsonProperty("companyEndYear") val companyEndYear: String?,
  @JsonProperty("jobData") val jobData: JobDataRequest,
)

data class JobDataRequest(
  @JsonProperty("positions") val positions: List<PositionRequest>,
  @JsonProperty("links") val links: List<LinkRequest>?,
) {
  data class PositionRequest(
    @JsonProperty("position") val position: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("isCurrentPosition") val isCurrentPosition: Boolean,
    @JsonProperty("positionStartMonth") val positionStartMonth: String,
    @JsonProperty("positionStartYear") val positionStartYear: String,
    @JsonProperty("positionEndMonth") val positionEndMonth: String?,
    @JsonProperty("positionEndYear") val positionEndYear: String?,
  )

  data class LinkRequest(
    @JsonProperty("name") val name: String,
    @JsonProperty("url") val url: String,
  )
}
