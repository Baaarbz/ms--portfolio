package com.barbzdev.portfolio.application

import com.barbzdev.portfolio.domain.Job
import com.barbzdev.portfolio.domain.repository.JobRepository

class GetAllJobsService(
  private val jobRepository: JobRepository
) {

  operator fun invoke(): List<GetJobsResponse> =
    findJobs()
      .orderByStarDate()
      .toResponse()

  private fun findJobs() = jobRepository.findAll()

  private fun List<Job>.orderByStarDate() = this.sortedByDescending { it.startDate.value.toLocalDate() }

  private fun List<Job>.toResponse(): List<GetJobsResponse> =
    this.map { job ->
      val tags = job.jobData.tags.map { it }
      val links = job.jobData.links?.map { LinkResponse(it.name, it.url) }
      val jobDataResponse = JobDataResponse(links, tags)
      GetJobsResponse(
        job.id.value,
        job.companyName.value,
        job.role.value,
        job.description.value,
        job.companyUrl.value,
        job.startDate.value.getDate(),
        job.endDate?.value?.getDate(),
        jobDataResponse
      )
    }
}

data class GetJobsResponse(
  val id: String,
  val companyName: String,
  val role: String,
  val description: String,
  val companyUrl: String,
  val startDate: String,
  val endDate: String?,
  val jobData: JobDataResponse,
)

data class JobDataResponse(
  val links: List<LinkResponse>?,
  val tags: List<String>?,
)

data class LinkResponse(
  val name: String,
  val url: String,
)
