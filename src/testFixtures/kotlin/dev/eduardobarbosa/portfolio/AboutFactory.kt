package dev.eduardobarbosa.portfolio

import com.github.javafaker.Faker
import dev.eduardobarbosa.portfolio.infrastructure.framework.repository.job.dto.AboutDTO
import java.util.UUID

object AboutFactory {
  private val faker = Faker.instance()
  fun anAboutDTO(): AboutDTO {
    val fakerCompany = faker.superhero();

    return AboutDTO(
      id = UUID.randomUUID().toString(),
      about = fakerCompany.descriptor(),
    )
  }
}
