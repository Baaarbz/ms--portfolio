package com.barbzdev.portfolio.application

import com.barbzdev.portfolio.domain.Job
import com.barbzdev.portfolio.domain.JobData
import com.barbzdev.portfolio.domain.common.AuditableDate.Companion.of
import com.barbzdev.portfolio.domain.repository.JobRepository
import com.barbzdev.portfolio.domain.valueobject.Link
import com.barbzdev.portfolio.domain.valueobject.Role
import com.barbzdev.portfolio.domain.valueobject.RoleDescription
import com.barbzdev.portfolio.domain.valueobject.RoleEndDate
import com.barbzdev.portfolio.domain.valueobject.RoleName
import com.barbzdev.portfolio.domain.valueobject.RoleStartDate
import com.barbzdev.portfolio.domain.valueobject.Tag

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
    val roles = jobData.roles.map { roleRequest ->
      Role(
        RoleName(roleRequest.role),
        RoleDescription(roleRequest.description),
        RoleStartDate(of(roleRequest.startDate)),
        roleRequest.endDate?.let { RoleEndDate(of(it)) }
      )
    }
    val tags = jobData.tags.map { Tag(it) }
    val jobDataDomain = JobData(roles, links, tags)

    return Job.create(companyName, companyUrl, startDate, endDate, jobDataDomain)
  }
}

data class CreateNewJobResponse(val id: String)

data class CreateNewJobRequest(
  val companyName: String,
  val companyUrl: String,
  val startDate: String,
  val endDate: String?,
  val jobData: JobDataRequest,
)

data class JobDataRequest(
  val roles: List<RoleRequest>,
  val links: List<LinkRequest>?,
  val tags: List<String>,
)

data class RoleRequest(
  val role: String,
  val description: String,
  val startDate: String,
  val endDate: String?
)

data class LinkRequest(
  val name: String,
  val url: String,
)
