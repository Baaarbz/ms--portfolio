package com.barbzdev.portfolio.infrastructure.framework.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class OpenAPIConfiguration {
  @Bean
  fun springShopOpenAPI(): OpenAPI {
    return OpenAPI()
      .info(
        Info()
          .title("Portfolio API")
          .description("Rest API for my personal website")
          .version("v0.0.1")
          .license(
            License()
              .name("MIT")
              .url("https://github.com/Baaarbz/ms--portfolio/blob/main/LICENSE")
          )
      )
      .addServersItem(
        Server()
          .url("https://barbzdev.com/portfolio/")
      )
  }
}
