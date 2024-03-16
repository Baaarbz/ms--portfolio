package com.barbzdev.portfolio.infrastructure.framework.configuration

import com.barbzdev.portfolio.application.CreateNewJobService
import com.barbzdev.portfolio.application.GetAllJobsService
import com.barbzdev.portfolio.domain.repository.JobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {

  @Bean
  fun getAllJobsService(jobRepository: JobRepository) = GetAllJobsService(jobRepository)

  @Bean
  fun createNewJobService(jobRepository: JobRepository) = CreateNewJobService(jobRepository)
}
