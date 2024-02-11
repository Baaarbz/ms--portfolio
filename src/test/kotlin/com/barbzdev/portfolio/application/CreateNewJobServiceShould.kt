package com.barbzdev.portfolio.application

import assertk.assertThat
import assertk.assertions.isBetween
import assertk.assertions.isEqualToIgnoringGivenProperties
import assertk.assertions.isNotNull
import com.barbzdev.portfolio.JobFactory
import com.barbzdev.portfolio.domain.common.AuditableDate
import com.barbzdev.portfolio.domain.common.AuditableDateTime
import com.barbzdev.portfolio.domain.valueobject.CompanyName
import com.barbzdev.portfolio.domain.valueobject.CompanyUrl
import com.barbzdev.portfolio.domain.valueobject.Id
import com.barbzdev.portfolio.domain.Job
import com.barbzdev.portfolio.domain.JobData
import com.barbzdev.portfolio.domain.valueobject.JobEndDate
import com.barbzdev.portfolio.domain.valueobject.JobStartDate
import com.barbzdev.portfolio.domain.valueobject.JobUpdatedAt
import com.barbzdev.portfolio.domain.valueobject.Link
import com.barbzdev.portfolio.domain.valueobject.Role
import com.barbzdev.portfolio.domain.valueobject.RoleDescription
import com.barbzdev.portfolio.domain.valueobject.RoleEndDate
import com.barbzdev.portfolio.domain.valueobject.RoleName
import com.barbzdev.portfolio.domain.valueobject.RoleStartDate
import com.barbzdev.portfolio.domain.valueobject.Tag
import com.barbzdev.portfolio.domain.repository.JobRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import java.time.OffsetDateTime
import org.junit.jupiter.api.Test

class CreateNewJobServiceShould {

  @MockK
  private lateinit var jobs: JobRepository

  @InjectMockKs
  private lateinit var service: CreateNewJobService

  @Test
  fun `given new job request save it and return generated id`() {
    val request = JobFactory.aJobCreateRequest()
    every { jobs.save(any()) } just Runs

    service(request)

    val expectedSavedJob = buildExpectedJobFrom(request)
    verify {
      jobs.save(withArg { actualJob ->
        assertThat(actualJob).isEqualToIgnoringGivenProperties(expectedSavedJob, Job::id, Job::updatedAt)
        assertThat(actualJob.id).isNotNull()
        assertThat(actualJob.updatedAt.value.toOffsetDateTime()).isBetween(OffsetDateTime.now(), OffsetDateTime.now().minusSeconds(5))
      })
    }
  }

  private fun buildExpectedJobFrom(request: CreateNewJobRequest): Job {
    val links = request.jobData.links?.map { Link(it.name, it.url) }
    val tags = request.jobData.tags.map { Tag(it) }
    val roles = request.jobData.roles.map { roleRequest ->
      Role(
        RoleName(roleRequest.role),
        RoleDescription(roleRequest.description),
        RoleStartDate(AuditableDate.of(roleRequest.startDate)),
        roleRequest.endDate?.let { RoleEndDate(AuditableDate.of(it)) }
      )
    }

    val jobData = JobData(roles, links, tags)

    return Job(
      Id.new(),
      CompanyName(request.companyName),
      CompanyUrl(request.companyUrl),
      JobStartDate(AuditableDate.of(request.startDate)),
      request.endDate?.let { JobEndDate(AuditableDate.of(it)) },
      jobData,
      JobUpdatedAt(AuditableDateTime.now()),
    )
  }
}
