package dev.eduardobarbosa.portfolio.infrastructure.framework.repository.job.dto

data class JobDataDTO(val positions: List<PositionDTO>) {
    data class PositionDTO(
        val position: String,
        val description: String,
        val isCurrentPosition: Boolean,
        val positionStartMonth: String,
        val positionStartYear: String,
        val positionEndMonth: String?,
        val positionEndYear: String?,
    )


}
