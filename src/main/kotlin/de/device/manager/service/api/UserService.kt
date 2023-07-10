package de.device.manager.service.api

import de.device.manager.controller.dto.UserDto
import de.device.manager.persistent.model.User
import java.util.UUID

interface UserService {

    fun getAllUsers(): List<User>
    fun findById(id: UUID): User
    fun createUser(userDto: UserDto): User

    fun assignDeviceToUser(userId: UUID, deviceId: UUID): User
}