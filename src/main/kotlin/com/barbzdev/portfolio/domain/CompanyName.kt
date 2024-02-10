package com.barbzdev.portfolio.domain


data class CompanyName(val value: String) {
  init {
    require(value.isNotBlank())
  }
}
