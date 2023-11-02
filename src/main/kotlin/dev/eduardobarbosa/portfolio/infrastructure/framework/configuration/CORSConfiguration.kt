package dev.eduardobarbosa.portfolio.infrastructure.framework.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class CORSConfiguration : WebMvcConfigurer {
  override fun addCorsMappings(registry: CorsRegistry) {
    registry.addMapping("/**")
      .allowedOrigins("http://localhost:3000") // Cambia esto al origen de tu cliente React
      .allowedMethods("GET")
      .allowCredentials(true)
  }
}
