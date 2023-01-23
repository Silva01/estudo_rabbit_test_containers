package br.net.silva.daniel.api.queue.test.rabbitmq.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig(
    @Value("\${api.queue.url}") private val urlQueue: String,
    @Value("\${api.exchange}") private val exchangeName: String,
    @Value("\${api.identifier}") private val identifier: String) {

    @Bean
    fun queue(): Queue = Queue(urlQueue, true)

    @Bean
    fun exchange(): TopicExchange = TopicExchange(exchangeName)

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange): Binding {
        return BindingBuilder.bind(queue).to(exchange).with(identifier)
    }
}