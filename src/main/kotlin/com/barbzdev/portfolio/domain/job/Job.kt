package com.barbzdev.portfolio.domain.job

import com.barbzdev.portfolio.domain.common.Month
import com.barbzdev.portfolio.domain.common.Year
import com.barbzdev.portfolio.domain.job.exception.InvalidDatesException
import java.net.URI
import java.util.UUID

data class Job(
  val id: Id,
  val companyName: CompanyName,
  val companyURL: CompanyURL,
  val isCurrentCompany: Boolean,
  val companyStartMonth: Month,
  val companyStartYear: Year,
  val companyEndMonth: Month?,
  val companyEndYear: Year?,
  val jobData: JobData,
) {

  init {
    if (joinEndDateAsNumber() != 0 && joinEndDateAsNumber() > joinStartDateAsNumber()) {
      throw InvalidDatesException("job end date can not be after job start date")
    }
    if (isCurrentCompany && joinEndDateAsNumber() > 0) {
      throw InvalidDatesException("if is current job can not have end date")
    }
    if (!isCurrentCompany && joinEndDateAsNumber() == 0) {
      throw InvalidDatesException("if is not current job must have defined an end date")
    }

    jobData.positions.forEach { position ->
      if (position.joinEndDateAsNumber() > joinEndDateAsNumber()) {
        throw InvalidDatesException("a position can not have and end date later than the job")
      }
      if (joinStartDateAsNumber() > position.joinStartDateAsNumber()) {
        throw InvalidDatesException("a position can not have and start earlier than the job")
      }
    }
  }

  fun joinStartDateAsNumber() = companyStartYear.value.toInt() * 100 + companyStartMonth.value.toInt()

  fun joinEndDateAsNumber() = if (companyEndMonth != null && companyEndYear != null) {
    companyEndYear.value.toInt() * 100 + companyEndYear.value.toInt()
  } else {
    0
  }

  data class Id(val value: String) {
    init {
      UUID.fromString(value)
    }
  }

  data class CompanyName(val value: String) {
    init {
      require(value.isNotBlank())
    }
  }

  data class CompanyURL(val value: String) {
    init {
      URI.create(value)
    }
  }
}
