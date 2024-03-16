package com.barbzdev.portfolio.domain.valueobject

data class Role(val value: String) {
  init {
    require(value.isNotBlank()) { "role can not be blank" }
  }
}
