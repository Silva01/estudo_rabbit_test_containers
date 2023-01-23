package br.net.silva.daniel.api.queue.test.rabbitmq.controller

import br.net.silva.daniel.api.queue.test.rabbitmq.integration.RabbitIntegrationAbstract
import br.net.silva.daniel.api.queue.test.rabbitmq.util.JsonConverter
import br.net.silva.daniel.api.queue.test.rabbitmq.value_object.PeopleMessage
import com.rabbitmq.client.ConnectionFactory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.junit.jupiter.Testcontainers


@Testcontainers
@Tag("integration")
@SpringBootTest
class MainControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Value("\${spring.rabbitmq.host}") private val host: String,
    @Value("\${spring.rabbitmq.port}") private val port: Int
): RabbitIntegrationAbstract() {

    @Test
    fun `should return a message`() {
        mockMvc.perform(get("/api/"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(""))
    }

    @Test
    fun `should return status 200`() {
        mockMvc.perform(get("/api/"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `should send message to queue`() {

        mockMvc.perform(get("/api/"))
            .andExpect(MockMvcResultMatchers.status().isOk)


        val message = PeopleMessage("Hello", "Daniel", 33)
        val json = JsonConverter.convert(message)

        val factoryRabbit = ConnectionFactory()
        factoryRabbit.host = host
        factoryRabbit.port = port

        val connection = factoryRabbit.newConnection()
        val channel = connection.createChannel()
        val response = channel.basicGet("test.queue.api", true)

        assertTrue(connection.isOpen)
        assertNotNull(response)

        if (response != null) {
            val body = String(response.body)
            assertEquals(json, body)
        }
    }
}