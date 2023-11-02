package com.barbzdev.portfolio.infrastructure.framework.configuration

import com.barbzdev.portfolio.domain.job.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {

    @Bean
    fun getAllJobsService(jobs: JobRepository) = com.barbzdev.portfolio.application.GetAllJobsService(jobs)
}