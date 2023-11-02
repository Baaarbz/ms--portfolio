package dev.eduardobarbosa.portfolio.domain.job

import dev.eduardobarbosa.portfolio.domain.common.Month
import dev.eduardobarbosa.portfolio.domain.common.Year

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
