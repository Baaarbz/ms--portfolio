package dev.eduardobarbosa.portfolio.infrastructure.repository

import dev.eduardobarbosa.portfolio.IntegrationTest
import dev.eduardobarbosa.portfolio.domain.about.AboutRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class JDBCAboutRepositoryShould : IntegrationTest() {

  @Autowired
  private lateinit var abouts: AboutRepository

  @Test
  fun `find las about me in db`() {
    val lastAbout = abouts.findLast()

    assertNotNull(lastAbout)
    assertEquals("last inserted about", lastAbout!!.about)
  }
}
