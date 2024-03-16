package com.barbzdev.portfolio.domain.valueobject

data class Description(val value: String) {
  init {
    require(value.isNotBlank())
  }
}
