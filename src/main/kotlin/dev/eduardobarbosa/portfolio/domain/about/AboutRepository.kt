package dev.eduardobarbosa.portfolio.domain.about

import dev.eduardobarbosa.portfolio.infrastructure.framework.repository.job.dto.AboutDTO

interface AboutRepository {
  fun findLast(): AboutDTO?
}
