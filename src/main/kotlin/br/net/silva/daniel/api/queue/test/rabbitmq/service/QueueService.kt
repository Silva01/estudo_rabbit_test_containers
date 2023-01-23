package br.net.silva.daniel.api.queue.test.rabbitmq.service

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

class QueueService(
    @Autowired private val rabbitTemplate: RabbitTemplate,
    @Value("\${api.exchange}") private val exchange: String,
    @Value("\${api.identifier}") private val identifier: String
) {

    fun send(message: String) {
        rabbitTemplate.convertAndSend(exchange, identifier, message)
    }

}