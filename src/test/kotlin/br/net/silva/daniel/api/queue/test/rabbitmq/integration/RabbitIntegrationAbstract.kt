package br.net.silva.daniel.api.queue.test.rabbitmq.integration

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.RabbitMQContainer

abstract class RabbitIntegrationAbstract {

    val rabbitContainer = RabbitMQContainer("rabbitmq:3.8.9-management")
        .withExposedPorts(5672, 15672)
        .withQueue("test.queue.api")
        .withBinding("test.queue.api", "test.exchange.api", "api")
        .withExchange("test.exchange.api", "direct")
        .withUser("guest", "guest")


    init {
        rabbitContainer.start()
    }

    fun close() {
        rabbitContainer.stop()
    }

    fun initialize(configurationApplicationContext: ConfigurableApplicationContext) {
        val values = TestPropertyValues.of(
            "spring.rabbitmq.host=" + rabbitContainer.host,
            "spring.rabbitmq.port=" + rabbitContainer.getMappedPort(5672),
        )
        values.applyTo(configurationApplicationContext)
    }
}