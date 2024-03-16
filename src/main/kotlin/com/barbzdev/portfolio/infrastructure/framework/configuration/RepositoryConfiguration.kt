package com.barbzdev.portfolio.infrastructure.framework.configuration

import com.barbzdev.portfolio.infrastructure.framework.repository.JpaJobDatasource
import com.barbzdev.portfolio.infrastructure.framework.repository.JpaJobRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfiguration {

  @Bean
  fun jpaJobRepository(jpaJobDatasource: JpaJobDatasource) = JpaJobRepository(jpaJobDatasource)
}
