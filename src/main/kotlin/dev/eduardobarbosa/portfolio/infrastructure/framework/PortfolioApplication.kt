package dev.eduardobarbosa.portfolio.infrastructure.framework

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PortfolioApplication

fun main(args: Array<String>) {
  runApplication<PortfolioApplication>(*args)
}
