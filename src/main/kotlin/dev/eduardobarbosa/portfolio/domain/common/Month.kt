package dev.eduardobarbosa.portfolio.domain.common

data class Month(val value: String) {
  init {
    val number = value.toInt()
    if (number <= 0 && value.length != 2) {
      throw IllegalArgumentException("The month must be a positive number and bigger than 0 and have length 2")
    }
  }
}
