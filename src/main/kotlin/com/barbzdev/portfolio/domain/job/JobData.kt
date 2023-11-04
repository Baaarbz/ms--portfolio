package com.barbzdev.portfolio.domain.job

import com.barbzdev.portfolio.domain.common.Month
import com.barbzdev.portfolio.domain.common.Year
import com.barbzdev.portfolio.domain.job.exception.InvalidJobDatesException

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
  ) {
    init {
      if (joinEndDateAsNumber() != 0 && joinEndDateAsNumber() < joinStartDateAsNumber()) {
        throw InvalidJobDatesException("position end date can not be after position start date")
      }
      if (isCurrentPosition && joinEndDateAsNumber() > 0) {
        throw InvalidJobDatesException("if is current position can not have end date")
      }
      if (!isCurrentPosition && joinEndDateAsNumber() == 0) {
        throw InvalidJobDatesException("if is not current position must have defined an end date")
      }
    }

    fun joinStartDateAsNumber() = positionStartYear.value.toInt() * 100 + positionStartMonth.value.toInt()

    fun joinEndDateAsNumber() = if (positionEndMonth != null && positionEndYear != null) {
      positionEndYear.value.toInt() * 100 + positionEndYear.value.toInt()
    } else {
      0
    }
  }

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
