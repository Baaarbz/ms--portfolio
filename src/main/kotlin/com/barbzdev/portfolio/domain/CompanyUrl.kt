package com.barbzdev.portfolio.domain

import java.net.URI

data class CompanyUrl(val value: String) {
  init {
    URI.create(value)
  }
}
