package de.device.manager.service.api

import de.device.manager.controller.dto.DeviceDto
import de.device.manager.persistent.model.Device
import java.util.*

interface DeviceService {

    fun findById(id: UUID): Device

    fun findAll(): List<Device>
    fun createDevice(deviceDto: DeviceDto): Device
}