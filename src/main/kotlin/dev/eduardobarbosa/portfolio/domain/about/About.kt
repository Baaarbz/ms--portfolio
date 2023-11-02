package dev.eduardobarbosa.portfolio.domain.about

import java.util.UUID

data class About(
  val id: Id,
  val about: About,
) {
  data class Id(val value: String) {
    init {
      UUID.fromString(value)
    }
  }

  data class About(val value: String) {
    init {
      require(value.isNotBlank())
    }
  }
}
