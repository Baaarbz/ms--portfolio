package com.barbzdev.portfolio.domain.valueobject

data class RoleDescription(val value: String) {
  init {
    require(value.isNotBlank())
  }
}
