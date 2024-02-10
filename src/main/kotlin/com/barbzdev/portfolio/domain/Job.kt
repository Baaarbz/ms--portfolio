package com.barbzdev.portfolio.domain

import com.barbzdev.portfolio.domain.AuditableDate.Companion.now
import com.barbzdev.portfolio.domain.AuditableDate.Companion.of
import com.barbzdev.portfolio.domain.exception.InvalidDateException

data class Job(
  val id: Id,
  val companyName: CompanyName,
  val companyUrl: CompanyUrl,
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
      companyUrl: String,
      startDate: String,
      endDate: String?,
      jobData: JobData,
    ) = Job(
      id = Id.new(),
      companyName = CompanyName(companyName),
      companyUrl = CompanyUrl(companyUrl),
      startDate = JobStartDate(of(startDate)),
      endDate = endDate?.let { JobEndDate(of(endDate)) },
      jobData = jobData,
      updatedAt = JobUpdatedAt(now())
    )

  }
}
