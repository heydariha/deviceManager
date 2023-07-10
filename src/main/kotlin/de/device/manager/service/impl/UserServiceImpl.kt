package de.device.manager.service.impl

import de.device.manager.controller.dto.UserDto
import de.device.manager.helper.ObjectTranslator
import de.device.manager.persistent.UserRepository
import de.device.manager.persistent.model.User
import de.device.manager.service.api.DeviceService
import de.device.manager.service.api.UserService
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class UserServiceImpl(private val userRepository: UserRepository,
                      private val deviceService: DeviceService,
                      private val translator: ObjectTranslator) : UserService {
    override fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun findById(id: UUID): User {
        return userRepository.findById(id)
                .orElseThrow { NoSuchElementException("User not found") }
    }

    override fun createUser(userDto: UserDto): User {
        return userRepository.save(translator.dtoToUser(userDto))
    }

    override fun assignDeviceToUser(userId: UUID, deviceId: UUID): User {
        val user = findById(userId)
        val device = deviceService.findById(deviceId)
        user.device = device
        return userRepository.save(user)
    }
}