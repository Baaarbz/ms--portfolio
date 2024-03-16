package com.barbzdev.portfolio.domain.repository

import com.barbzdev.portfolio.domain.Job
import com.barbzdev.portfolio.domain.valueobject.Id

interface JobRepository {
  fun findAll(): List<Job>

  fun findBy(id: Id): Job?

  fun save(job: Job)
}
