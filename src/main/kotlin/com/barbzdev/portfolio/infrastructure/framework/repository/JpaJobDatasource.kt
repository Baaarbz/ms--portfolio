package com.barbzdev.portfolio.infrastructure.framework.repository

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface JpaJobDatasource : JpaRepository<JpaJob, UUID>

@Entity
@Table(name = "jobs")
data class JpaJob(
  @Id
  val id: UUID,
  val companyName: String,
  val description: String,
  val role: String,
  val companyUrl: String,
  val startDate: LocalDate,
  val endDate: LocalDate?,
  val jobData: String,
  val updatedAt: OffsetDateTime
)
