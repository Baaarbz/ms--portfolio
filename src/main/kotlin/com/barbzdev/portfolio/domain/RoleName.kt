package com.barbzdev.portfolio.domain

data class RoleName(val value: String) {
  init {
    require(value.isNotBlank())
  }
}