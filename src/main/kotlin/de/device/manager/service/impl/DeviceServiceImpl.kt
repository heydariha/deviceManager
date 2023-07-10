package de.device.manager.service.impl

import de.device.manager.controller.dto.DeviceDto
import de.device.manager.helper.ObjectTranslator
import de.device.manager.persistent.DeviceRepository
import de.device.manager.persistent.model.Device
import de.device.manager.service.api.DeviceService
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException


@Service
class DeviceServiceImpl(private val deviceRepository: DeviceRepository, private val translator: ObjectTranslator) : DeviceService {
    override fun createDevice(deviceDto: DeviceDto): Device {
        return deviceRepository.save(translator.dtoToDevice(deviceDto))
    }

    override fun findById(id: UUID): Device {
        return deviceRepository.findById(id)
                .orElseThrow { NoSuchElementException("Device not found") }
    }

    override fun findAll(): List<Device> {
        return deviceRepository.findAll()
    }

}