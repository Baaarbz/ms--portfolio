package com.barbzdev.portfolio.domain.common

import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class AuditableDateTime private constructor(private val date: String) {
  companion object {
    fun now(): AuditableDateTime = AuditableDateTime(
      OffsetDateTime
        .now()
        .withOffsetSameInstant(ZoneOffset.UTC)
        .truncatedTo(ChronoUnit.MICROS)
        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    )

    fun of(value: OffsetDateTime): AuditableDateTime = AuditableDateTime(
      value
        .withOffsetSameInstant(ZoneOffset.UTC)
        .truncatedTo(ChronoUnit.MICROS)
        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    )
  }

  fun toOffsetDateTime(): OffsetDateTime = OffsetDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

  fun getDate(): String = date
}
