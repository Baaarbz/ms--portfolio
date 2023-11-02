package dev.eduardobarbosa.portfolio.infrastructure.framework.repository.job

import dev.eduardobarbosa.portfolio.domain.about.AboutRepository
import dev.eduardobarbosa.portfolio.infrastructure.framework.repository.job.dto.AboutDTO
import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class JDBCAboutRepository(
  private val jdbcTemplate: NamedParameterJdbcTemplate,
) : AboutRepository {

  private val logger = LoggerFactory.getLogger(this::class.java)

  private val rowMapper: RowMapper<AboutDTO> = RowMapper { rs, _ ->
    AboutDTO(
      id = rs.getString("id"),
      about = rs.getString("about"),
    )
  }

  override fun findLast(): AboutDTO? {
    val sql = "SELECT * FROM abouts order by updated_at desc limit :limit;"
    logger.debug(sql)

    return jdbcTemplate.queryForObject(
      sql,
      mapOf("limit" to 1),
      rowMapper
    )
  }
}
