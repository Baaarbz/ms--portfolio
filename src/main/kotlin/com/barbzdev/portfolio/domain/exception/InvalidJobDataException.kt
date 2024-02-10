package com.barbzdev.portfolio.domain.exception

import com.barbzdev.portfolio.domain.exception.BadRequestException

class InvalidJobDataException(override val message: String) : BadRequestException(message)
