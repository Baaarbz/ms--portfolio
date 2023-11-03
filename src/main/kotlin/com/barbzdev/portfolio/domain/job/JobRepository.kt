package com.barbzdev.portfolio.domain.job

interface JobRepository {
  fun findAll(): List<Job>

  fun findBy(id: Job.Id): Job?
}
