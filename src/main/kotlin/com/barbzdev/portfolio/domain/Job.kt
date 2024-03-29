package com.barbzdev.portfolio.domain

import com.barbzdev.portfolio.domain.common.AuditableDate.Companion.of
import com.barbzdev.portfolio.domain.common.AuditableDateTime
import com.barbzdev.portfolio.domain.exception.InvalidDateException
import com.barbzdev.portfolio.domain.valueobject.CompanyName
import com.barbzdev.portfolio.domain.valueobject.CompanyUrl
import com.barbzdev.portfolio.domain.valueobject.Description
import com.barbzdev.portfolio.domain.valueobject.Id
import com.barbzdev.portfolio.domain.valueobject.JobEndDate
import com.barbzdev.portfolio.domain.valueobject.JobStartDate
import com.barbzdev.portfolio.domain.valueobject.JobUpdatedAt
import com.barbzdev.portfolio.domain.valueobject.Role

data class Job(
  val id: Id,
  val companyName: CompanyName,
  val companyUrl: CompanyUrl,
  val description: Description,
  val role: Role,
  val startDate: JobStartDate,
  val endDate: JobEndDate?,
  val jobData: JobData,
  val updatedAt: JobUpdatedAt
) {

  init {
    if (endDate != null && startDate.value.toLocalDate().isAfter(endDate.value.toLocalDate())) {
      throw InvalidDateException("job end date can not be after job start date")
    }
  }

  companion object {
    fun create(
      companyName: String,
      description: String,
      role: String,
      companyUrl: String,
      startDate: String,
      endDate: String?,
      jobData: JobData,
    ) = Job(
      id = Id.new(),
      companyName = CompanyName(companyName),
      role = Role(role),
      description = Description(description),
      companyUrl = CompanyUrl(companyUrl),
      startDate = JobStartDate(of(startDate)),
      endDate = endDate?.let { JobEndDate(of(endDate)) },
      jobData = jobData,
      updatedAt = JobUpdatedAt(AuditableDateTime.now())
    )

  }
}
