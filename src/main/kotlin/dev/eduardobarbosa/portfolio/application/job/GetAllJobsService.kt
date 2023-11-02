package dev.eduardobarbosa.portfolio.application.job

import dev.eduardobarbosa.portfolio.domain.job.JobRepository
import dev.eduardobarbosa.portfolio.domain.job.toDomain
import dev.eduardobarbosa.portfolio.domain.job.toHttpGetAllResponse
import dev.eduardobarbosa.portfolio.infrastructure.framework.controller.HttpGetJobsResponse

class GetAllJobsService(
  private val jobs: JobRepository
) {

  fun execute(): List<HttpGetJobsResponse> = jobs.findAll()
    .map { it.toDomain() }
    .sortedByDescending { it.joinStartDateAsNumber() }
    .map { it.toHttpGetAllResponse() }
}
