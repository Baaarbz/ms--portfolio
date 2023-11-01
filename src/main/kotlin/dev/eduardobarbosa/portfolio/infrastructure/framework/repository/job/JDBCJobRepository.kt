package dev.eduardobarbosa.portfolio.infrastructure.framework.repository.job

import dev.eduardobarbosa.portfolio.domain.job.JobRepository
import dev.eduardobarbosa.portfolio.infrastructure.framework.repository.CommonRepositoryUtil.Companion.gson
import dev.eduardobarbosa.portfolio.infrastructure.framework.repository.job.dto.JobDTO
import dev.eduardobarbosa.portfolio.infrastructure.framework.repository.job.dto.JobDataDTO
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class JDBCJobRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) : JobRepository {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val rowMapper: RowMapper<JobDTO> = RowMapper { rs, _ ->
        JobDTO(
            id = rs.getString("id"),
            companyName = rs.getString("company_name"),
            companyURL = rs.getString("company_url"),
            isCurrentCompany = rs.getBoolean("current_job"),
            companyStartMonth = rs.getString("company_start_month"),
            companyStartYear = rs.getString("company_start_year"),
            companyEndMonth = rs.getString("company_end_month"),
            companyEndYear = rs.getString("company_end_year"),
            jobData = gson.fromJson(rs.getString("jobData"), JobDataDTO::class.java),
        )
    }

    override fun findAll(): List<JobDTO> {
        val sql = "SELECT * FROM jobs"
        logger.debug(sql)

        return jdbcTemplate.query(
            sql,
            rowMapper
        )
    }
}