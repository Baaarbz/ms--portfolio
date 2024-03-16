package com.barbzdev.portfolio.application

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.barbzdev.portfolio.JobFactory
import com.barbzdev.portfolio.domain.Job
import com.barbzdev.portfolio.domain.JobData
import com.barbzdev.portfolio.domain.repository.JobRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class GetAllJobsServiceShould {

  private val jobRepository = mockk<JobRepository>()
  private val service = GetAllJobsService(jobRepository)

  @Test
  fun `retrieve all jobs ordered by dates`() {
    val listOfJobs = listOf(
      JobFactory.aJob(),
      JobFactory.aJob(),
      JobFactory.aJob(),
      JobFactory.aJob(),
      JobFactory.aJob()
    )
    every { jobRepository.findAll() } returns listOfJobs

    val response = service()

    val expectedResponse = buildExpectedJobsResponseFrom(listOfJobs)
    assertThat(response).isEqualTo(expectedResponse)
  }

  private fun buildExpectedJobsResponseFrom(jobs: List<Job>) = jobs
    .sortedByDescending { it.startDate.value.toLocalDate() }
    .map { jobDomain ->
      GetJobsResponse(
        id = jobDomain.id.value,
        companyName = jobDomain.companyName.value,
        companyUrl = jobDomain.companyUrl.value,
        description = jobDomain.description.value,
        role = jobDomain.role.value,
        startDate = jobDomain.startDate.value.getDate(),
        endDate = jobDomain.endDate?.value?.getDate(),
        jobData = buildExpectedJobDataFrom(jobDomain.jobData)
      )
    }
}

private fun buildExpectedJobDataFrom(jobData: JobData) = JobDataResponse(
  links = jobData.links?.map { LinkResponse(it.name, it.url) },
  tags = jobData.tags
)
