package dev.eduardobarbosa.portfolio.domain.about

import dev.eduardobarbosa.portfolio.infrastructure.framework.controller.HttpGetAboutResponse
import dev.eduardobarbosa.portfolio.infrastructure.framework.repository.job.dto.AboutDTO


fun AboutDTO.toDomain() = About(
  id = About.Id(this.id),
  about = About.About(about)
)

fun About.toHttpGetAllResponse() = HttpGetAboutResponse(
  id = this.id.value,
  about = this.about.value
)
