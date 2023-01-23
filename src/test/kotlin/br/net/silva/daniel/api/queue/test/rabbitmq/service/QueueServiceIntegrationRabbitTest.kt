package br.net.silva.daniel.api.queue.test.rabbitmq.service

import br.net.silva.daniel.api.queue.test.rabbitmq.util.JsonConverter
import br.net.silva.daniel.api.queue.test.rabbitmq.value_object.PeopleMessage
import com.rabbitmq.client.ConnectionFactory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@Tag("integration")
@SpringBootTest
class QueueServiceIntegrationRabbitTest(
    @Value("\${api.exchange}") private val exchange: String,
    @Value("\${api.identifier}") private val identifier: String
) {

    @Container
    val rabbitContainer = RabbitMQContainer("rabbitmq:3-management").also {
        it.withExposedPorts(5672, 15672)
        it.withAccessToHost(true)
        it.withExchange(exchange, "direct")
        it.withQueue("test.queue.api")
    }

    @Test
    fun `should send message to queue`() {
        val connectionFactory = CachingConnectionFactory(rabbitContainer.host).apply {
            username = rabbitContainer.adminUsername
            setPassword(rabbitContainer.adminPassword)
            port = rabbitContainer.amqpPort
        }

        val service = QueueService(RabbitTemplate(connectionFactory), exchange, identifier)

        assertTrue(rabbitContainer.isRunning)

        println(rabbitContainer.getMappedPort(5672))
        val message = PeopleMessage("Hello", "Daniel", 33)
        val json = JsonConverter.convert(message)
        service.send(json)

        val factoryRabbit = ConnectionFactory()
        factoryRabbit.host = rabbitContainer.host
        factoryRabbit.port = rabbitContainer.amqpPort

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