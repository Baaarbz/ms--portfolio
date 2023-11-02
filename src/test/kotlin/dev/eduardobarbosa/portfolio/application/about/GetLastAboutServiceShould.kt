package dev.eduardobarbosa.portfolio.application.about

import dev.eduardobarbosa.portfolio.AboutFactory
import dev.eduardobarbosa.portfolio.UnitTest
import dev.eduardobarbosa.portfolio.domain.about.AboutRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetLastAboutServiceShould : UnitTest() {

  @MockK
  private lateinit var abouts: AboutRepository

  @InjectMockKs
  private lateinit var service: GetLastAboutService

  @Test
  fun `retrieve one job as HttResponse model`() {
    val anAbout = AboutFactory.anAboutDTO()
    every { abouts.findLast() } returns anAbout

    val response = service.execute()

    assertEquals(anAbout.id, response!!.id)
    assertEquals(anAbout.about, response.about)
  }
}
