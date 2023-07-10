package de.device.manager.helper

import de.device.manager.controller.dto.DeviceDto
import de.device.manager.controller.dto.UserDto
import de.device.manager.persistent.model.Device
import de.device.manager.persistent.model.User
import org.springframework.stereotype.Service
import java.util.*

@Service
public class ObjectTranslator {
    fun userToDto(user: User): UserDto {
        return UserDto(
                id = user.id,
                firstName = user.firstName,
                lastName = user.lastName,
                address = user.address,
                birthday = user.birthday,
                device = user.device?.let { deviceToDto(it) }
        )
    }

    fun dtoToUser(userDto: UserDto): User {
        return User(
                id = userDto.id ?: UUID.randomUUID(),
                firstName = userDto.firstName,
                lastName = userDto.lastName,
                address = userDto.address,
                birthday = userDto.birthday
        )
    }

    fun deviceToDto(device: Device): DeviceDto{
        return DeviceDto(
                id = device.uuid,
                serialNumber = device.serialNumber,
                phoneNumber = device.phoneNumber,
                model = device.model
                )
    }

    fun dtoToDevice(deviceDto: DeviceDto): Device{
        return Device(
                uuid = deviceDto.id ?: UUID.randomUUID(),
                serialNumber = deviceDto.serialNumber,
                phoneNumber = deviceDto.phoneNumber,
                model = deviceDto.model
        )
    }

}