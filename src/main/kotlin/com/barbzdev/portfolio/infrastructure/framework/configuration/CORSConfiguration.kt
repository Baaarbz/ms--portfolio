package com.barbzdev.portfolio.infrastructure.framework.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class CORSConfiguration : WebMvcConfigurer {
  override fun addCorsMappings(registry: CorsRegistry) {
    registry.addMapping("/**")
      .allowedOrigins("http://localhost:3000", "https://barbzdev.com")
      .allowedMethods("GET", "POST", "PUT", "DELETE")
      .allowCredentials(true)
  }
}
