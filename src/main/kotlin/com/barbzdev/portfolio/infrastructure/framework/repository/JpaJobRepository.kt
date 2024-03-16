package com.barbzdev.portfolio.infrastructure.framework.repository

import com.barbzdev.portfolio.domain.Job
import com.barbzdev.portfolio.domain.JobData
import com.barbzdev.portfolio.domain.common.AuditableDate
import com.barbzdev.portfolio.domain.common.AuditableDateTime
import com.barbzdev.portfolio.domain.repository.JobRepository
import com.barbzdev.portfolio.domain.valueobject.CompanyName
import com.barbzdev.portfolio.domain.valueobject.CompanyUrl
import com.barbzdev.portfolio.domain.valueobject.Description
import com.barbzdev.portfolio.domain.valueobject.Id
import com.barbzdev.portfolio.domain.valueobject.JobEndDate
import com.barbzdev.portfolio.domain.valueobject.JobStartDate
import com.barbzdev.portfolio.domain.valueobject.JobUpdatedAt
import com.barbzdev.portfolio.domain.valueobject.Role
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

class JpaJobRepository(private val datasource: JpaJobDatasource) : JobRepository {

  override fun findAll(): List<Job> = datasource
    .findAll()
    .map { it.toDomain() }

  override fun findBy(id: Id): Job? = datasource
    .findById(UUID.fromString(id.value))
    .getOrNull()
    ?.toDomain()

  override fun save(job: Job) {
    datasource.save(job.toJpa())
  }

  private fun JpaJob.toDomain() = Job(
    id = Id(id.toString()),
    companyName = CompanyName(companyName),
    description = Description(description),
    role = Role(role),
    companyUrl = CompanyUrl(companyUrl),
    startDate = JobStartDate(AuditableDate.of(startDate)),
    endDate = endDate?.let { JobEndDate(AuditableDate.of(endDate)) },
    jobData = jacksonObjectMapper().readValue<JobData>(jobData),
    updatedAt = JobUpdatedAt(AuditableDateTime.of(updatedAt))
  )

  private fun Job.toJpa() = JpaJob(
    id = UUID.fromString(id.value),
    companyName = companyName.value,
    role = role.value,
    description = description.value,
    companyUrl = companyUrl.value,
    startDate = startDate.value.toLocalDate(),
    endDate = endDate?.let { endDate.value.toLocalDate() },
    jobData = jacksonObjectMapper().writeValueAsString(jobData),
    updatedAt = updatedAt.value.toOffsetDateTime()
  )
}
