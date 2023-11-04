package com.barbzdev.portfolio.infrastructure.framework.repository.job

import com.barbzdev.portfolio.domain.common.Month
import com.barbzdev.portfolio.domain.common.Year
import com.barbzdev.portfolio.domain.job.JobData

data class JsonJobData(val positions: List<Position>) {
  data class Position(
    val position: String,
    val description: String,
    val isCurrentPosition: Boolean,
    val positionStartMonth: String,
    val positionStartYear: String,
    val positionEndMonth: String?,
    val positionEndYear: String?,
  )
}

fun JsonJobData.toDomain() = JobData(
  positions = this.positions.map { it.toDomain() }
)

private fun JsonJobData.Position.toDomain() = JobData.Position(
  position = JobData.PositionName(this.position),
  description = JobData.Description(this.description),
  isCurrentPosition = this.isCurrentPosition,
  positionStartMonth = Month(this.positionStartMonth),
  positionStartYear = Year(this.positionStartYear),
  positionEndMonth = this.positionEndMonth?.let { Month(it) },
  positionEndYear = this.positionEndYear?.let { Year(it) },
)

fun JobData.toJson() = JsonJobData(
  positions = this.positions.map { it.toJson() }
)

private fun JobData.Position.toJson() = JsonJobData.Position(
  position = this.position.value,
  description = this.description.value,
  isCurrentPosition = this.isCurrentPosition,
  positionStartMonth = this.positionStartMonth.value,
  positionStartYear = this.positionStartYear.value,
  positionEndMonth = this.positionEndMonth?.value,
  positionEndYear = this.positionEndYear?.value,
)
