package dev.eduardobarbosa.portfolio.infrastructure.framework.controller

import com.fasterxml.jackson.annotation.JsonProperty
import dev.eduardobarbosa.portfolio.application.about.GetLastAboutService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/abouts")
class AboutController(
  private val getLastAboutService: GetLastAboutService
) {

  @GetMapping
  fun getJobs(): ResponseEntity<HttpGetAboutResponse> {
    val response = getLastAboutService.execute()
    return response
      ?.let { ResponseEntity.ok(it) }
      ?: ResponseEntity.noContent().build()
  }


}

data class HttpGetAboutResponse(
  @JsonProperty("id") val id: String,
  @JsonProperty("about") val about: String,
)
