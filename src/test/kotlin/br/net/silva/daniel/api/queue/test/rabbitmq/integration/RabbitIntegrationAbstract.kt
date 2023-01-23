package br.net.silva.daniel.api.queue.test.rabbitmq.integration

import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.RabbitMQContainer

@RunWith(SpringRunner::class)
@SpringBootTest
@ContextConfiguration(initializers = [RabbitIntegrationAbstract.Initializer::class])
@AutoConfigureMockMvc
abstract class RabbitIntegrationAbstract {

    companion object {
        val rabbitContainer = RabbitMQContainer("rabbitmq:3.8.9-management").apply {
            withExposedPorts(5672, 15672)
            withQueue("test.queue.api")
            withUser("guest", "guest")
        }
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            rabbitContainer.start()

            TestPropertyValues.of(
                "spring.rabbitmq.host=${rabbitContainer.host}",
                "spring.rabbitmq.username=${rabbitContainer.adminUsername}",
                "spring.rabbitmq.password=${rabbitContainer.adminPassword}",
                "spring.rabbitmq.port=${rabbitContainer.amqpPort}"
            ).applyTo(configurableApplicationContext.environment)
        }
    }
}