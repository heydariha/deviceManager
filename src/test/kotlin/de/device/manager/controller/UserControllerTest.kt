package de.device.manager.controller

import de.device.manager.controller.dto.UserDto
import de.device.manager.helper.ObjectTranslator
import de.device.manager.persistent.DeviceRepository
import de.device.manager.persistent.UserRepository
import de.device.manager.persistent.model.Device
import de.device.manager.persistent.model.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.web.client.RestTemplate
import java.time.LocalDate
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private var port: Int = 0

    private lateinit var restTemplate: RestTemplate
    private lateinit var baseUrl: String

    @Autowired
    private lateinit var translator: ObjectTranslator

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var deviceRepository: DeviceRepository

    private lateinit var sampleDevice: Device
    private lateinit var sampleUser: User
    private lateinit var sampleUserWithDevice: User

    @BeforeEach
    fun setUp() {
        restTemplate = RestTemplate()
        baseUrl = "http://localhost:$port/users"

        userRepository.deleteAll()
        deviceRepository.deleteAll()
        sampleDevice = deviceRepository.save(Device(UUID.randomUUID(), "serial Number", "phone number", "model"))

        sampleUser = userRepository.save(User(UUID.randomUUID(), "John", "Doe", "Address", LocalDate.now()))

        sampleUser.device = sampleDevice
        sampleUserWithDevice = userRepository.save(sampleUser)
    }

    @Test
    fun testCreateUser() {
        //GIVEN
        val userDto = UserDto(null, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))

        //WHEN
        val response = restTemplate.postForObject(baseUrl, userDto, UserDto::class.java)

        //THEN
        assertNotNull(response)
        assertEquals("John", response!!.firstName)
        assertEquals("Doe", response.lastName)
        assertEquals("123 Main St", response.address)
        assertEquals(LocalDate.of(1990, 1, 1), response.birthday)
    }

    @Test
    fun testAssignDeviceToUser() {
        //GIVEN
        val url = "$baseUrl/${sampleUser.id}/assign/${sampleDevice.uuid}"

        //WHEN
        val response = restTemplate.postForObject(url, null, UserDto::class.java)

        //THEN
        assertNotNull(response)
        assertEquals(translator.userToDto(sampleUser), response)
    }

    @Test
    fun testGetAllUsersWithDevices() {
        //WHEN
        val response = restTemplate.getForObject("$baseUrl/all", Array<UserDto>::class.java)

        //THEN
        assertNotNull(response)
        assertEquals(translator.userToDto(sampleUserWithDevice), response!!.first())
    }

    @Test
    fun testGetAllUsers() {
        //WHEN
        val response = restTemplate.getForObject("$baseUrl/all", Array<UserDto>::class.java)

        //THEN
        assertNotNull(response)
        assertEquals(translator.userToDto(sampleUser), response!!.first())
    }
}
