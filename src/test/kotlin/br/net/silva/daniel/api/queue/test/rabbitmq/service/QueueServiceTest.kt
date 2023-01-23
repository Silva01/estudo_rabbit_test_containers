package br.net.silva.daniel.api.queue.test.rabbitmq.service

import br.net.silva.daniel.api.queue.test.rabbitmq.util.JsonConverter
import br.net.silva.daniel.api.queue.test.rabbitmq.value_object.PeopleMessage
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.amqp.rabbit.core.RabbitTemplate

@Tag("unit")
class QueueServiceTest {

    private val rabbitTemplate = mock(RabbitTemplate::class.java)
    private val service = QueueService(rabbitTemplate, "test.queue.api", "api")

    @Test
    fun `should send message to queue`() {
        val message = PeopleMessage("Hello", "Daniel", 33)
        val json = JsonConverter.convert(message)
        service.send(json)
        verify(rabbitTemplate).convertAndSend("test.queue.api", "api", json)
    }
}