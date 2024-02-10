package com.barbzdev.portfolio.domain

import com.barbzdev.portfolio.domain.exception.InvalidDateException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class AuditableDate private constructor(val date: String) {
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

    fun now(): AuditableDate = AuditableDate(LocalDate.now().format(formatter))
  }

  fun toLocalDate(): LocalDate = LocalDate.parse(date, formatter);

}
