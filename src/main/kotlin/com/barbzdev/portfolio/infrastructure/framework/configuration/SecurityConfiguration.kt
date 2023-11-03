package com.barbzdev.portfolio.infrastructure.framework.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.boot.actuate.health.HealthEndpoint
import org.springframework.boot.actuate.info.InfoEndpoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfiguration(
  @Value("\${com.barbzdev.auth.usr}") private val username: String,
  @Value("\${com.barbzdev.auth.pwd}") private val password: String,
) {

  @Bean
  @Throws(Exception::class)
  fun configureSecurityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain =
    httpSecurity
      .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
      .csrf { it.disable() }
      .authorizeHttpRequests {
        it
          .requestMatchers(
            HttpMethod.GET, "/api/v1/jobs"
          )
          .permitAll()
          .requestMatchers(
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
          )
          .permitAll()
          .requestMatchers(
            EndpointRequest.to(HealthEndpoint::class.java),
            EndpointRequest.to(InfoEndpoint::class.java)
          )
          .permitAll()
          .anyRequest()
          .authenticated()
      }
      .httpBasic {}
      .build()

  @Bean
  fun configureUserDetailsService(): UserDetailsService =
    InMemoryUserDetailsManager(
      User
        .withUsername(username)
        .password(passwordEncoder().encode(password))
        .build()
    )

  @Bean
  fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}
