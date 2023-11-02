package dev.eduardobarbosa.portfolio.application.about

import dev.eduardobarbosa.portfolio.domain.about.AboutRepository
import dev.eduardobarbosa.portfolio.domain.about.toDomain
import dev.eduardobarbosa.portfolio.domain.about.toHttpGetAllResponse
import dev.eduardobarbosa.portfolio.infrastructure.framework.controller.HttpGetAboutResponse

class GetLastAboutService(
  private val abouts: AboutRepository
) {

  fun execute(): HttpGetAboutResponse? = abouts.findLast()?.toDomain()?.toHttpGetAllResponse()

}
