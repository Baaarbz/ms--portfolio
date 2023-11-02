package com.barbzdev.portfolio.domain.job

import com.barbzdev.portfolio.infrastructure.framework.repository.job.dto.JobDTO

interface JobRepository {
    fun findAll(): List<JobDTO>
}