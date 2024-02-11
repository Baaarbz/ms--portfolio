package com.barbzdev.portfolio.domain.valueobject

import java.net.URI

data class Link(val name: String, val url: String) {
  init {
    require(name.isNotBlank())
    URI.create(url)
  }
}
