package dev.eduardobarbosa.portfolio.infrastructure.repository

import dev.eduardobarbosa.portfolio.IntegrationTest
import dev.eduardobarbosa.portfolio.domain.job.JobRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class JDBCJobRepositoryShould : IntegrationTest() {

  @Autowired
  private lateinit var jobs: JobRepository

  @Test
  fun `find all jobs in db`() {
    val jobsFound = jobs.findAll()

    assertEquals(2, jobsFound.size)
    assertEquals("7cd9a4dc-ff61-48da-af2c-c839d6572b3a", jobsFound[0].id)
    assertEquals("86c144f4-0e1d-408b-b274-106ef8939b4b", jobsFound[1].id)
    assertEquals(1, jobsFound[0].jobData.positions.size)
    assertEquals(1, jobsFound[1].jobData.positions.size)
  }
}
