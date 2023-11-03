package com.barbzdev.portfolio.domain.job.exception

import com.barbzdev.portfolio.domain.common.exception.BadRequestException

class InvalidJobDatesException(override val message: String) : BadRequestException(message)
