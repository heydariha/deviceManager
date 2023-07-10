package de.device.manager.controller

import com.fasterxml.jackson.databind.ObjectMapper
import de.device.manager.controller.dto.DeviceDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.web.client.RestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeviceControllerTest {

    @LocalServerPort
    private var port: Int = 0

    private lateinit var restTemplate: RestTemplate
    private lateinit var baseUrl: String

    @BeforeEach
    fun setUp() {
        restTemplate = RestTemplate()
        baseUrl = "http://localhost:$port/devices"
    }

    @Test
    fun testGetAllDevices() {
        //GIVEN
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)

        //WHEN
        val response = sendRequest(HttpMethod.GET, headers, null)

        //THEN
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun testCreateDevice() {
        //GIVEN
        val headers = HttpHeaders()
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.contentType = MediaType.APPLICATION_JSON
        val deviceDto = DeviceDto(null, "Serial Number", "Phone number", "Model")

        //WHEN
        val response = sendRequest(HttpMethod.POST, headers, deviceDto)

        //THEN
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Serial Number", ObjectMapper().readValue(response.body, DeviceDto::class.java).serialNumber)
    }

    private fun sendRequest(method: HttpMethod, headers: HttpHeaders, body: Any?): ResponseEntity<String> {
        val entity = HttpEntity(body, headers)
        return restTemplate.exchange(baseUrl, method, entity, String::class.java)
    }
}
