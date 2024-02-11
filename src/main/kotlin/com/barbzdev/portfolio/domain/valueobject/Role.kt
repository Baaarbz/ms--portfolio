package com.barbzdev.portfolio.domain.valueobject

import com.barbzdev.portfolio.domain.common.AuditableDate.Companion.of
import com.barbzdev.portfolio.domain.exception.InvalidDateException

data class Role(
  val name: RoleName,
  val description: RoleDescription,
  val startDate: RoleStartDate,
  val endDate: RoleEndDate?
) {
  init {
    if (endDate != null && startDate.value.toLocalDate().isAfter(endDate.value.toLocalDate())) {
      throw InvalidDateException("position end date can not be after position start date")
    }
  }

  companion object {
    fun create(
      roleName: String,
      roleDescription: String,
      roleStartDate: String,
      roleEndDate: String?
    ) = Role(
      name = RoleName(roleName),
      description = RoleDescription(roleDescription),
      startDate = RoleStartDate(of(roleStartDate)),
      endDate = roleEndDate?.let { RoleEndDate(of(roleEndDate)) },
    )
  }
}

