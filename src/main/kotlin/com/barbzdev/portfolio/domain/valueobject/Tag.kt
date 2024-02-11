package com.barbzdev.portfolio.domain.valueobject


data class Tag(val value: String) {
  init {
    require(value.isNotBlank())
  }
}
