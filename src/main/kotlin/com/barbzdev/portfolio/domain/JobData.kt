package com.barbzdev.portfolio.domain

import com.barbzdev.portfolio.domain.valueobject.Link
import com.barbzdev.portfolio.domain.valueobject.Role
import com.barbzdev.portfolio.domain.valueobject.Tag

data class JobData(
  val roles: List<Role>,
  val links: List<Link>?,
  val tags: List<Tag>
)
