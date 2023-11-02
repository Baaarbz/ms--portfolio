package com.barbzdev.portfolio.domain.job

import com.barbzdev.portfolio.domain.common.Month
import com.barbzdev.portfolio.domain.common.Year
import com.barbzdev.portfolio.infrastructure.framework.controller.HttpGetJobsResponse
import com.barbzdev.portfolio.infrastructure.framework.controller.JobDataResponse
import com.barbzdev.portfolio.infrastructure.framework.repository.job.dto.JobDTO
import com.barbzdev.portfolio.infrastructure.framework.repository.job.dto.JobDataDTO

fun JobDTO.toDomain() = Job(
        id = Job.Id(this.id),
        companyName = Job.CompanyName(this.companyName),
        companyURL = Job.CompanyURL(this.companyURL),
        isCurrentCompany = this.isCurrentCompany,
        companyStartMonth = Month(this.companyStartMonth),
        companyStartYear = Year(this.companyStartYear),
        companyEndMonth = this.companyEndMonth?.let { Month(it) },
        companyEndYear = this.companyEndYear?.let { Year(it) },
        jobData = this.jobData.toDomain()
)

private fun JobDataDTO.toDomain() = JobData(
        positions = this.positions.map { it.toDomain() }
)

private fun JobDataDTO.PositionDTO.toDomain() = JobData.Position(
        position = JobData.PositionName(this.position),
        description = JobData.Description(this.description),
        isCurrentPosition = this.isCurrentPosition,
        positionStartMonth = Month(this.positionStartMonth),
        positionStartYear = Year(this.positionStartYear),
        positionEndMonth = this.positionEndMonth?.let { Month(it) },
        positionEndYear = this.positionEndYear?.let { Year(it) },
)

fun Job.toHttpGetAllResponse() = HttpGetJobsResponse(
        id = this.id.value,
        companyName = this.companyName.value,
        companyURL = this.companyURL.value,
        isCurrentCompany = this.isCurrentCompany,
        companyStartMonth = this.companyStartMonth.value,
        companyStartYear = this.companyStartYear.value,
        companyEndMonth = this.companyEndMonth?.value,
        companyEndYear = this.companyEndYear?.value,
        jobData = this.jobData.toHttpGetAllResponse()
)

private fun JobData.toHttpGetAllResponse() = JobDataResponse(
        positions = this.positions.map { it.toHttpGetAllResponse() }
)

private fun JobData.Position.toHttpGetAllResponse() = JobDataResponse.PositionResponse(
        position = this.position.value,
        description = this.description.value,
        isCurrentPosition = this.isCurrentPosition,
        positionStartMonth = this.positionStartMonth.value,
        positionStartYear = this.positionStartYear.value,
        positionEndMonth = this.positionEndMonth?.value,
        positionEndYear = this.positionEndYear?.value,
)