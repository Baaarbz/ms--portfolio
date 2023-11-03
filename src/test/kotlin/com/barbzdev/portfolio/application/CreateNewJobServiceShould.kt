package com.barbzdev.portfolio.application

import com.barbzdev.portfolio.JobFactory
import com.barbzdev.portfolio.UnitTest
import com.barbzdev.portfolio.domain.job.Job
import com.barbzdev.portfolio.domain.job.JobRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateNewJobServiceShould : UnitTest() {

  @MockK
  private lateinit var jobs: JobRepository

  @InjectMockKs
  private lateinit var service: CreateNewJobService

  @Test
  fun `given new job request save it and return generated id`() {
    val slot = slot<Job>()
    val aJobRequest = JobFactory.aJobCreateRequest()
    every { jobs.save(capture(slot)) } just Runs

    service.execute(aJobRequest)

    val capturedJob = slot.captured
    assertEquals(aJobRequest.companyName, capturedJob.companyName.value)
    assertEquals(aJobRequest.companyURL, capturedJob.companyURL.value)
    assertEquals(aJobRequest.companyStartMonth, capturedJob.companyStartMonth.value)
    assertEquals(aJobRequest.companyStartYear, capturedJob.companyStartYear.value)
    assertEquals(aJobRequest.companyEndMonth, capturedJob.companyEndMonth?.value)
    assertEquals(aJobRequest.companyEndYear, capturedJob.companyEndYear?.value)
    assertEquals(aJobRequest.jobData.positions[0].position, capturedJob.jobData.positions[0].position.value)
  }
}
