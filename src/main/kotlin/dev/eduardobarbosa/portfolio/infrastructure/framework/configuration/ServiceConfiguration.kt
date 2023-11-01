package dev.eduardobarbosa.portfolio.infrastructure.framework.configuration

import dev.eduardobarbosa.portfolio.application.GetAllJobsService
import dev.eduardobarbosa.portfolio.domain.job.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {

    @Bean
    fun getAllJobsService(jobs: JobRepository) = GetAllJobsService(jobs)
}