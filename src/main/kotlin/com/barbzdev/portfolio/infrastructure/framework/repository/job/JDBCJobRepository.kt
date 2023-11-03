package com.barbzdev.portfolio.infrastructure.framework.repository.job

import com.barbzdev.portfolio.domain.common.Month
import com.barbzdev.portfolio.domain.common.Year
import com.barbzdev.portfolio.domain.job.Job
import com.barbzdev.portfolio.domain.job.JobRepository
import com.barbzdev.portfolio.infrastructure.framework.repository.CommonRepositoryUtil.Companion.gson
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class JDBCJobRepository(
  private val jdbcTemplate: NamedParameterJdbcTemplate,
) : JobRepository {

  private val logger = LoggerFactory.getLogger(this::class.java)

  private val rowMapper: RowMapper<Job> = RowMapper { rs, _ ->
    Job(
      id = Job.Id(rs.getString("id")),
      companyName = Job.CompanyName(rs.getString("company_name")),
      companyURL = Job.CompanyURL(rs.getString("company_url")),
      isCurrentCompany = rs.getBoolean("current_job"),
      companyStartMonth = Month(rs.getString("company_start_month")),
      companyStartYear = Year(rs.getString("company_start_year")),
      companyEndMonth = rs.getString("company_end_month")?.let { Month(it) },
      companyEndYear = rs.getString("company_end_year")?.let { Year(it) },
      jobData = gson.fromJson(rs.getString("jobData"), JsonJobData::class.java).toDomain(),
    )
  }

  override fun findAll(): List<Job> {
    val sql = "SELECT * FROM jobs"
    logger.debug(sql)

    return jdbcTemplate.query(
      sql,
      rowMapper
    )
  }

  override fun findBy(id: Job.Id): Job? {
    val sql = "SELECT * FROM jobs WHERE id = :id"
    logger.debug(sql)

    return jdbcTemplate.queryForObject(
      sql,
      mapOf("id" to id.value),
      rowMapper
    )
  }

  override fun save(job: Job) {
    TODO("Not yet implemented")
  }
}
