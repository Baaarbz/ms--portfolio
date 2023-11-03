package com.barbzdev.portfolio.domain.job.exception

import com.barbzdev.portfolio.domain.common.exception.BadRequestException

class InvalidJobDataException(override val message: String) : BadRequestException(message)
