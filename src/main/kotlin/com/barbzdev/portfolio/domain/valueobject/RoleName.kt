package com.barbzdev.portfolio.domain.valueobject

data class RoleName(val value: String) {
  init {
    require(value.isNotBlank())
  }
}
