package com.barbzdev.portfolio.domain

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class AuditableDateTime private constructor(private val date: String) {
  companion object {
    fun now(): AuditableDateTime = AuditableDateTime(
      OffsetDateTime
        .now()
        .truncatedTo(ChronoUnit.NANOS)
        .format(DateTimeFormatter.ISO_DATE)
    )
  }

  fun toOffsetDateTime(): OffsetDateTime = OffsetDateTime.parse(date, DateTimeFormatter.ISO_DATE)
}
