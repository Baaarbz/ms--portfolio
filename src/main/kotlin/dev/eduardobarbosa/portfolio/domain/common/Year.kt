package dev.eduardobarbosa.portfolio.domain.common

data class Year(val value: String) {
  init {
    val number = value.toInt()
    if (number <= 0 && value.length != 4) {
      throw IllegalArgumentException("The year must be a positive number and bigger then 0 and have length 4")
    }
  }
}
