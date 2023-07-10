package de.device.manager.controller.dto

import jakarta.validation.constraints.NotEmpty
import org.springframework.lang.NonNull
import java.util.*

data class DeviceDto (
        val id: UUID? = null,
        @NonNull val serialNumber: String,
        @NotEmpty val phoneNumber: String,
        @NotEmpty val model: String
){
    constructor() : this(null, "", "", "")
}