package com.barbzdev.portfolio.domain.job

import com.barbzdev.portfolio.domain.common.Month
import com.barbzdev.portfolio.domain.common.Year

data class JobData(
  val positions: List<Position>,
) {
  data class Position(
    val position: PositionName,
    val description: Description,
    val isCurrentPosition: Boolean,
    val positionStartMonth: Month,
    val positionStartYear: Year,
    val positionEndMonth: Month?,
    val positionEndYear: Year?,
  )

  data class PositionName(val value: String) {
    init {
      require(value.isNotBlank())
    }
  }

  data class Description(val value: String) {
    init {
      require(value.isNotBlank())
    }
  }
}
