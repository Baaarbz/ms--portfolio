package com.barbzdev.portfolio.infrastructure.framework.controller.exception

import com.barbzdev.portfolio.domain.common.exception.BadRequestException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerController {

  @ExceptionHandler(BadRequestException::class)
  fun handleBadRequestException(exception: BadRequestException): ResponseEntity<HttpErrorResponse> {
    return ResponseEntity
      .badRequest()
      .body(exception.toHttpResponse())
  }

}

private fun RuntimeException.toHttpResponse(): HttpErrorResponse =
  HttpErrorResponse(this.message ?: "no error message available")

data class HttpErrorResponse(val message: String)
