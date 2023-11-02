package dev.eduardobarbosa.portfolio.domain.job

import dev.eduardobarbosa.portfolio.domain.common.Month
import dev.eduardobarbosa.portfolio.domain.common.Year
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
  fun joinStartDateAsNumber() = companyStartYear.value.toInt() * 100 + companyStartMonth.value.toInt()

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
