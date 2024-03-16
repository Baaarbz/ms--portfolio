package com.barbzdev.portfolio.infrastructure.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isEqualToIgnoringGivenProperties
import com.barbzdev.IntegrationTest
import com.barbzdev.portfolio.JobFactory
import com.barbzdev.portfolio.domain.Job
import com.barbzdev.portfolio.domain.repository.JobRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class JpaJobRepositoryShould : IntegrationTest() {

  @Autowired
  private lateinit var jobs: JobRepository

  @Test
  fun `find all jobs in db`() {
    val jobsFound = jobs.findAll()

    assertThat(jobsFound.size).isEqualTo(2)
  }

  @Test
  fun `save new job in db`() {
    val aJob = JobFactory.aJob()

    jobs.save(aJob)

    val savedJob = jobs.findBy(aJob.id)!!
    assertThat(savedJob).isEqualToIgnoringGivenProperties(aJob, Job::updatedAt, Job::endDate, Job::startDate)
    assertThat(savedJob.updatedAt.value.getDate()).isEqualTo(aJob.updatedAt.value.getDate())
    assertThat(savedJob.startDate.value.getDate()).isEqualTo(aJob.startDate.value.getDate())
    assertThat(savedJob.endDate?.value?.getDate()).isEqualTo(aJob.endDate?.value?.getDate())
  }
}
