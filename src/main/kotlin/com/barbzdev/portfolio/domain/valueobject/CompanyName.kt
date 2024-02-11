package com.barbzdev.portfolio.domain.valueobject


data class CompanyName(val value: String) {
  init {
    require(value.isNotBlank())
  }
}
