package dev.eduardobarbosa.portfolio.domain.job

import dev.eduardobarbosa.portfolio.infrastructure.framework.repository.job.dto.JobDTO

interface JobRepository {
    fun findAll(): List<JobDTO>
}