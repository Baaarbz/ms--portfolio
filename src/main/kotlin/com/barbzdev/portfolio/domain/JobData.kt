package com.barbzdev.portfolio.domain

data class JobData(
  val roles: List<Role>,
  val links: List<Link>?,
  val tags: List<Tag>
)
