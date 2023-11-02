package com.barbzdev.portfolio.infrastructure.framework.repository.job.dto

data class JobDTO(
        val id: String,
        val companyName: String,
        val companyURL: String,
        val isCurrentCompany: Boolean,
        val companyStartMonth: String,
        val companyStartYear: String,
        val companyEndMonth: String?,
        val companyEndYear: String?,
        val jobData: JobDataDTO,
)
