package com.barbzdev.portfolio.domain.repository

import com.barbzdev.portfolio.domain.Job

interface JobRepository {
  fun findAll(): List<Job>

  fun findBy(id: Job.Id): Job?

  fun save(job: Job)
}
