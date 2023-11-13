package com.barbzdev.portfolio.application

import com.barbzdev.portfolio.domain.job.Job
import com.barbzdev.portfolio.domain.job.JobData
import com.barbzdev.portfolio.domain.job.JobRepository
import com.fasterxml.jackson.annotation.JsonProperty

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
    positions = this.positions.map { it.toHttpGetAllResponse() },
    links = this.links?.map { JobDataResponse.LinkResponse(it.name.value, it.url.value) },
    tags = this.tags.map { it.value }
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

data class HttpGetJobsResponse(
  @JsonProperty("id") val id: String,
  @JsonProperty("companyName") val companyName: String,
  @JsonProperty("companyURL") val companyURL: String,
  @JsonProperty("isCurrentCompany") val isCurrentCompany: Boolean,
  @JsonProperty("companyStartMonth") val companyStartMonth: String,
  @JsonProperty("companyStartYear") val companyStartYear: String,
  @JsonProperty("companyEndMonth") val companyEndMonth: String?,
  @JsonProperty("companyEndYear") val companyEndYear: String?,
  @JsonProperty("jobData") val jobData: JobDataResponse,
)

data class JobDataResponse(
  @JsonProperty("positions") val positions: List<PositionResponse>,
  @JsonProperty("links") val links: List<LinkResponse>?,
  @JsonProperty("tags") val tags: List<String>?,
) {
  data class PositionResponse(
    @JsonProperty("position") val position: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("isCurrentPosition") val isCurrentPosition: Boolean,
    @JsonProperty("positionStartMonth") val positionStartMonth: String,
    @JsonProperty("positionStartYear") val positionStartYear: String,
    @JsonProperty("positionEndMonth") val positionEndMonth: String?,
    @JsonProperty("positionEndYear") val positionEndYear: String?,
  )

  data class LinkResponse(
    @JsonProperty("name") val name: String,
    @JsonProperty("url") val url: String,
  )
}
