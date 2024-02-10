package com.barbzdev.portfolio.domain

data class RoleDescription(val value: String) {
  init {
    require(value.isNotBlank())
  }
}
