package de.device.manager.controller.dto

import jakarta.validation.constraints.NotEmpty
import java.time.LocalDate
import java.util.*

data class UserDto(
        val id: UUID?,
        @NotEmpty
        val firstName: String,
        @NotEmpty
        val lastName: String,
        @NotEmpty
        val address: String,
        val birthday: LocalDate,
        val device: DeviceDto? = null
){
        constructor() : this(null, "", "", "", LocalDate.now(), null)
}