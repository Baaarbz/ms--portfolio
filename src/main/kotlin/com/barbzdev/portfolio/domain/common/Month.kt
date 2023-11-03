package com.barbzdev.portfolio.domain.common

data class Month(val value: String) {
  init {
    val number = value.toInt()
    if (number <= 0 || number > 12 && value.length != 2) {
      throw IllegalArgumentException("The month must be a positive number and bigger than 0 but equal or lower than 12 and have length 2")
    }
  }
}
