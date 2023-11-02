package com.barbzdev.portfolio.application

import com.barbzdev.portfolio.domain.job.JobRepository
import com.barbzdev.portfolio.domain.job.toDomain
import com.barbzdev.portfolio.domain.job.toHttpGetAllResponse
import com.barbzdev.portfolio.infrastructure.framework.controller.HttpGetJobsResponse

class GetAllJobsService(
        private val jobs: JobRepository
) {

    fun execute(): List<HttpGetJobsResponse> = jobs.findAll()
            .map { it.toDomain() }
            .sortedByDescending { it.joinStartDateAsNumber() }
            .map { it.toHttpGetAllResponse() }
}