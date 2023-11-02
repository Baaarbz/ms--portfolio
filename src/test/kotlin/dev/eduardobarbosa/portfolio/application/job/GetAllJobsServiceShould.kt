package dev.eduardobarbosa.portfolio.application.job

import dev.eduardobarbosa.portfolio.JobFactory
import dev.eduardobarbosa.portfolio.UnitTest
import dev.eduardobarbosa.portfolio.domain.job.JobRepository
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
    val aJob = JobFactory.aJobDTO()
    val listOfJobs = listOf(aJob)
    every { jobs.findAll() } returns listOfJobs

    val response = service.execute()

    assertEquals(aJob.id, response[0].id)
    assertEquals(aJob.companyName, response[0].companyName)
    assertEquals(aJob.companyURL, response[0].companyURL)
    assertEquals(aJob.companyStartMonth, response[0].companyStartMonth)
    assertEquals(aJob.companyStartYear, response[0].companyStartYear)
    assertEquals(aJob.companyEndMonth, response[0].companyEndMonth)
    assertEquals(aJob.companyEndYear, response[0].companyEndYear)
    assertEquals(aJob.jobData.positions[0].position, response[0].jobData.positions[0].position)
  }

  @Test
  fun `retrieve five jobs as HttResponse model ordered by date`() {
    val listOfJobs = listOf(
      JobFactory.aJobDTO(),
      JobFactory.aJobDTO(),
      JobFactory.aJobDTO(),
      JobFactory.aJobDTO(),
      JobFactory.aJobDTO()
    )
    every { jobs.findAll() } returns listOfJobs

    val response = service.execute()

    assertTrue(response[0].companyStartYear.toInt() > response[4].companyStartYear.toInt())
  }
}
