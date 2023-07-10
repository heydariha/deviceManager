package de.device.manager.controller

import de.device.manager.controller.dto.UserDto
import de.device.manager.helper.ObjectTranslator
import de.device.manager.service.api.UserService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService,
                     private val translator: ObjectTranslator) {
    @PostMapping
    fun createUser(@RequestBody userDto: UserDto): UserDto {
        return translator.userToDto(userService.createUser(userDto))
    }

    @PostMapping("/{userId}/assign/{deviceId}")
    fun assignDeviceToUser(
            @PathVariable userId: UUID,
            @PathVariable deviceId: UUID
    ): UserDto {
        return translator.userToDto(userService.assignDeviceToUser(userId, deviceId))
    }

    @GetMapping
    fun getAllUsersWithDevices(): List<UserDto> {
        return userService.getAllUsers().filter { it.device != null }.map {
            translator.userToDto(it)
        }
    }

    @GetMapping("/all")
    fun getAllUsers(): List<UserDto> {
        return userService.getAllUsers().map {
            translator.userToDto(it)
        }
    }
}