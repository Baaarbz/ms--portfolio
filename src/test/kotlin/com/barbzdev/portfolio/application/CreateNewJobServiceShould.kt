package com.barbzdev.portfolio.application

import assertk.assertThat
import assertk.assertions.isBetween
import assertk.assertions.isEqualToIgnoringGivenProperties
import assertk.assertions.isNotNull
import com.barbzdev.portfolio.JobFactory
import com.barbzdev.portfolio.domain.Job
import com.barbzdev.portfolio.domain.JobData
import com.barbzdev.portfolio.domain.common.AuditableDate
import com.barbzdev.portfolio.domain.common.AuditableDateTime
import com.barbzdev.portfolio.domain.repository.JobRepository
import com.barbzdev.portfolio.domain.valueobject.CompanyName
import com.barbzdev.portfolio.domain.valueobject.CompanyUrl
import com.barbzdev.portfolio.domain.valueobject.Description
import com.barbzdev.portfolio.domain.valueobject.Id
import com.barbzdev.portfolio.domain.valueobject.JobEndDate
import com.barbzdev.portfolio.domain.valueobject.JobStartDate
import com.barbzdev.portfolio.domain.valueobject.JobUpdatedAt
import com.barbzdev.portfolio.domain.valueobject.Link
import com.barbzdev.portfolio.domain.valueobject.Role
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class CreateNewJobServiceShould {

  private val jobRepository = mockk<JobRepository>()
  private val service = CreateNewJobService(jobRepository)

  @Test
  fun `given new job request save it and return generated id`() {
    val request = JobFactory.aJobCreateRequest()
    every { jobRepository.save(any()) } just Runs

    service(request)

    val expectedJob = buildExpectedJobFrom(request)
    verify {
      jobRepository.save(withArg { actualJob ->
        assertThat(actualJob).isEqualToIgnoringGivenProperties(expectedJob, Job::id, Job::updatedAt, Job::startDate, Job::endDate)
        assertThat(actualJob.id).isNotNull()
        assertThat(actualJob.updatedAt.value.toOffsetDateTime()).isBetween(
          AuditableDateTime.now().toOffsetDateTime().minusSeconds(5),
          AuditableDateTime.now().toOffsetDateTime()
        )
      })
    }
  }

  private fun buildExpectedJobFrom(request: CreateNewJobRequest): Job {
    val links = request.jobData.links?.map { Link(it.name, it.url) }

    val jobData = JobData(links, request.jobData.tags)

    return Job(
      Id.new(),
      CompanyName(request.companyName),
      CompanyUrl(request.companyUrl),
      Description(request.description),
      Role(request.role),
      JobStartDate(AuditableDate.of(request.startDate)),
      request.endDate?.let { JobEndDate(AuditableDate.of(it)) },
      jobData,
      JobUpdatedAt(AuditableDateTime.now()),
    )
  }
}
