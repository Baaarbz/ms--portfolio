package com.barbzdev.portfolio.domain.valueobject

import java.net.URI

data class CompanyUrl(val value: String) {
  init {
    URI.create(value)
  }
}
