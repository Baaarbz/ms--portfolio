package com.barbzdev.portfolio.domain


data class Tag(val value: String) {
  init {
    require(value.isNotBlank())
  }
}
