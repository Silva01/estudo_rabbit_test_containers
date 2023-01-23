package br.net.silva.daniel.api.queue.test.rabbitmq.service

import br.net.silva.daniel.api.queue.test.rabbitmq.integration.RabbitIntegrationAbstract
import br.net.silva.daniel.api.queue.test.rabbitmq.util.JsonConverter
import br.net.silva.daniel.api.queue.test.rabbitmq.value_object.PeopleMessage
import com.rabbitmq.client.ConnectionFactory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@Tag("integration")
@SpringBootTest
class QueueServiceIntegrationRabbitTest(
    @Autowired private val rabbitTemplate: RabbitTemplate,
    @Value("\${api.exchange}") private val exchange: String,
    @Value("\${api.identifier}") private val identifier: String,
    @Value("\${spring.rabbitmq.host}") private val host: String,
    @Value("\${spring.rabbitmq.port}") private val port: Int,
): RabbitIntegrationAbstract() {

    @Test
    fun `should send message to queue`() {

        val service = QueueService(rabbitTemplate, exchange, identifier)
        val message = PeopleMessage("Hello", "Daniel", 33)
        val json = JsonConverter.convert(message)
        service.send(json)

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