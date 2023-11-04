package com.barbzdev.portfolio.application

import com.barbzdev.portfolio.JobFactory
import com.barbzdev.portfolio.UnitTest
import com.barbzdev.portfolio.domain.job.JobRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GetAllJobsServiceShould : UnitTest() {

  @MockK
  private lateinit var jobs: JobRepository

  @InjectMockKs
  private lateinit var service: GetAllJobsService

  @Test
  fun `retrieve one job as HttResponse model`() {
    val aJob = JobFactory.aJob()
    val listOfJobs = listOf(aJob)
    every { jobs.findAll() } returns listOfJobs

    val response = service.execute()

    assertEquals(aJob.id.value, response[0].id)
    assertEquals(aJob.companyName.value, response[0].companyName)
    assertEquals(aJob.companyURL.value, response[0].companyURL)
    assertEquals(aJob.companyStartMonth.value, response[0].companyStartMonth)
    assertEquals(aJob.companyStartYear.value, response[0].companyStartYear)
    assertEquals(aJob.companyEndMonth?.value, response[0].companyEndMonth)
    assertEquals(aJob.companyEndYear?.value, response[0].companyEndYear)
    assertEquals(aJob.jobData.positions[0].position.value, response[0].jobData.positions[0].position)
  }

  @Test
  fun `retrieve five jobs as HttResponse model ordered by date`() {
    val listOfJobs = listOf(
      JobFactory.aJob(),
      JobFactory.aJob(),
      JobFactory.aJob(),
      JobFactory.aJob(),
      JobFactory.aJob()
    )
    every { jobs.findAll() } returns listOfJobs

    val response = service.execute()

    assertTrue(response[0].companyStartYear.toInt() >= response[4].companyStartYear.toInt())
  }
}
