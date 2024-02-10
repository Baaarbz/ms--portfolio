package com.barbzdev.portfolio.domain.exception

class InvalidDateException(override val message: String) : BadRequestException(message)
