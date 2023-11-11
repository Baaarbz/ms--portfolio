package com.barbzdev.portfolio.infrastructure.framework.repository.job

import com.barbzdev.portfolio.domain.common.Month
import com.barbzdev.portfolio.domain.common.Year
import com.barbzdev.portfolio.domain.job.JobData

data class JsonJobData(val positions: List<Position>, val links: List<Link>?) {
  data class Position(
    val position: String,
    val description: String,
    val isCurrentPosition: Boolean,
    val positionStartMonth: String,
    val positionStartYear: String,
    val positionEndMonth: String?,
    val positionEndYear: String?,
  )

  data class Link(
    val name: String,
    val url: String,
  )
}

fun JsonJobData.toDomain() = JobData(
  positions = this.positions.map { it.toDomain() },
  links = this.links?.map { JobData.Link(name = JobData.LinkName(it.name), url = JobData.LinkURL(it.url)) }
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
  positions = this.positions.map { it.toJson() },
  links = this.links?.map { JsonJobData.Link(it.name.value, it.url.value) }
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
