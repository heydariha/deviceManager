package de.device.manager.controller

import de.device.manager.controller.dto.DeviceDto
import de.device.manager.helper.ObjectTranslator
import de.device.manager.service.api.DeviceService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/devices")
class DeviceController(private val deviceService: DeviceService,
                       private val translator: ObjectTranslator) {

    @GetMapping
    fun getAllDevices(): List<DeviceDto> {
        return deviceService.findAll().map { translator.deviceToDto(it) }
    }

    @PostMapping
    fun createDevice(@RequestBody @Valid deviceDto: DeviceDto): DeviceDto {
        return translator.deviceToDto(deviceService.createDevice(deviceDto))
    }
}