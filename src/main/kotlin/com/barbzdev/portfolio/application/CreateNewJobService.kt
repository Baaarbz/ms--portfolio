package com.barbzdev.portfolio.application

import com.barbzdev.portfolio.domain.Job
import com.barbzdev.portfolio.domain.JobData
import com.barbzdev.portfolio.domain.repository.JobRepository
import com.barbzdev.portfolio.domain.valueobject.Link

class CreateNewJobService(
  private val jobRepository: JobRepository
) {

  operator fun invoke(request: CreateNewJobRequest) = request
    .toDomain()
    .save()
    .getResponse()

  private fun Job.save(): Job {
    jobRepository.save(this)
    return this
  }

  private fun Job.getResponse() = CreateNewJobResponse(id.value)

  private fun CreateNewJobRequest.toDomain(): Job {
    val links = jobData.links?.map { Link(it.name, it.url) }
    val jobDataDomain = JobData(links, jobData.tags)

    return Job.create(companyName, description, role, companyUrl, startDate, endDate, jobDataDomain)
  }
}

data class CreateNewJobResponse(val id: String)

data class CreateNewJobRequest(
  val companyName: String,
  val description: String,
  val role: String,
  val companyUrl: String,
  val startDate: String,
  val endDate: String?,
  val jobData: JobDataRequest,
)

data class JobDataRequest(
  val links: List<LinkRequest>?,
  val tags: List<String>,
)

data class LinkRequest(
  val name: String,
  val url: String,
)
