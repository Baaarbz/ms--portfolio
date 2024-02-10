package com.barbzdev.portfolio.domain

import java.util.UUID

data class Id(val value: String) {
  init {
    UUID.fromString(value)
  }

  fun uuid(): UUID = UUID.fromString(value)

  companion object {
    fun new() = Id(UUID.randomUUID().toString())
  }
}
