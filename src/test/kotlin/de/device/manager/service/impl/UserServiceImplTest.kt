package de.device.manager.service.impl

import de.device.manager.controller.dto.UserDto
import de.device.manager.helper.ObjectTranslator
import de.device.manager.persistent.DeviceRepository
import de.device.manager.persistent.UserRepository
import de.device.manager.persistent.model.Device
import de.device.manager.persistent.model.User
import de.device.manager.service.api.DeviceService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDate
import java.util.*

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var deviceRepository: DeviceRepository

    @Autowired
    private lateinit var deviceService: DeviceService

    @Autowired
    private lateinit var translator: ObjectTranslator

    private lateinit var userService: UserServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userService = UserServiceImpl(userRepository, deviceService, translator)
    }

    @Test
    fun testGetAllUsers() {
        // GIVEN
        val users = listOf(User(UUID.randomUUID(), "John", "Doe", "Address", LocalDate.now()))
        `when`(userRepository.findAll()).thenReturn(users)

        // WHEN
        val result = userService.getAllUsers()

        // THEN
        assertEquals(users, result)
        verify(userRepository).findAll()
    }

    @Test
    fun testFindById() {
        // GIVEN
        val userId = UUID.randomUUID()
        val user = User(UUID.randomUUID(), "John", "Doe", "Address", LocalDate.now())
        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))

        // WHEN
        val result = userService.findById(userId)

        // THEN
        assertEquals(user, result)
        verify(userRepository).findById(userId)
    }

    @Test
    fun testFindByIdNotFound() {
        // GIVEN
        val userId = UUID.randomUUID()
        `when`(userRepository.findById(userId)).thenReturn(Optional.empty())

        // WHEN/THEN
        assertThrows<NoSuchElementException> {
            userService.findById(userId)
        }
        verify(userRepository).findById(userId)
    }

    @Test
    fun testCreateUser() {
        // GIVEN
        val user = User(UUID.randomUUID(), "John", "Doe", "Address", LocalDate.now())
        val userDto = UserDto(user.id, "John", "Doe", "Address", LocalDate.now())

        `when`(userRepository.save(user)).thenReturn(user)

        // WHEN
        val result = userService.createUser(userDto)

        // THEN
        assertEquals(user, result)
        verify(userRepository).save(user)
    }

    @Test
    fun testCreateInvalidUserShouldThrowException() {
        // GIVEN
        val user = User(UUID.randomUUID(), "", "", "Address", LocalDate.now())
        val userDto = UserDto(user.id, "John", "Doe", "Address", LocalDate.now())

        `when`(userRepository.save(user)).thenReturn(user)

        // WHEN
        assertThrows<NullPointerException> {
            userService.createUser(userDto)
        }
    }

    @Test
    fun testAssignDeviceToUser() {
        // GIVEN
        val userId = UUID.randomUUID()
        val deviceId = UUID.randomUUID()
        val user = User(userId, "John", "Doe", "Address", LocalDate.now())
        val device = Device(deviceId, "serial Number", "phone number", "model")
        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
        `when`(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device))
        `when`(userRepository.save(user)).thenReturn(user)

        // WHEN
        val result = userService.assignDeviceToUser(userId, deviceId)

        // THEN
        assertEquals(user, result)
        assertEquals(device, user.device)
        verify(userRepository).save(user)
    }
}
