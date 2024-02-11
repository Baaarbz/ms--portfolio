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
      val tags = job.jobData.tags.map { it.value }
      val links = job.jobData.links?.map { LinkResponse(it.name, it.url) }
      val roles = job.jobData.roles.map {
        RoleResponse(
          it.name.value,
          it.description.value,
          it.startDate.value.getDate(),
          it.endDate?.value?.getDate()
        )
      }
      val jobDataResponse = JobDataResponse(roles, links, tags)
      GetJobsResponse(
        job.id.value,
        job.companyName.value,
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
  val companyUrl: String,
  val startDate: String,
  val endDate: String?,
  val jobData: JobDataResponse,
)

data class JobDataResponse(
  val roles: List<RoleResponse>,
  val links: List<LinkResponse>?,
  val tags: List<String>?,
)

data class RoleResponse(
  val name: String,
  val description: String,
  val startDate: String,
  val endDate: String?,
)

data class LinkResponse(
  val name: String,
  val url: String,
)
