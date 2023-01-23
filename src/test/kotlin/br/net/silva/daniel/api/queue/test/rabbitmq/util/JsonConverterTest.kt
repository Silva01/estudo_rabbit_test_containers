package br.net.silva.daniel.api.queue.test.rabbitmq.util

import br.net.silva.daniel.api.queue.test.rabbitmq.value_object.PeopleMessage
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class JsonConverterTest {

    @Test
    fun `should convert object to json`() {
        val message = PeopleMessage("Hello", "Daniel", 33)
        val json = JsonConverter.convert(message)
        assertEquals("{\"message\":\"Hello\",\"name\":\"Daniel\",\"age\":33}", json)
    }

    @Test
    fun `should convert instance object to json`() {
        val message = PeopleMessage("Hello", "Daniel", 33)
        val converter = JsonConverter()
        val json = converter.convert(message)
        assertEquals("{\"message\":\"Hello\",\"name\":\"Daniel\",\"age\":33}", json)
    }
}