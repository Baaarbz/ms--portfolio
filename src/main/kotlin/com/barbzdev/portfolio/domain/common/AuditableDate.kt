package com.barbzdev.portfolio.domain.common

import com.barbzdev.portfolio.domain.exception.InvalidDateException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class AuditableDate private constructor(private val date: String) {

  fun getDate(): String = date

  companion object {

    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    fun of(value: String): AuditableDate {
      try {
        LocalDate.parse(value, formatter)
        return AuditableDate(value)
      } catch (exception: DateTimeParseException) {
        throw InvalidDateException("cannot parse date::$value message::${exception.message}")
      }
    }

    fun of(value: LocalDate): AuditableDate {
      try {
        val formatDate = value.format(formatter)
        return AuditableDate(formatDate)
      } catch (exception: DateTimeParseException) {
        throw InvalidDateException("cannot parse date::$value message::${exception.message}")
      }
    }

    fun now(): AuditableDate = AuditableDate(LocalDate.now().format(formatter))
  }

  fun toLocalDate(): LocalDate = LocalDate.parse(date, formatter)

}
