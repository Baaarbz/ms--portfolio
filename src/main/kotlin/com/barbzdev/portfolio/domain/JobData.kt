package com.barbzdev.portfolio.domain

import com.barbzdev.portfolio.domain.valueobject.Link

data class JobData(
  val links: List<Link>?,
  val tags: List<String>
)
