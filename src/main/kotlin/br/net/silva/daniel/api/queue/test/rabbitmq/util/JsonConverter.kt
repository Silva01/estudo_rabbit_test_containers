package br.net.silva.daniel.api.queue.test.rabbitmq.util

import com.fasterxml.jackson.databind.ObjectMapper

class JsonConverter {
    companion object {
        fun <T> convert(obj: T): String {
            return ObjectMapper().writeValueAsString(obj)
        }
    }

    fun <T> convert(obj: T): String {
        return ObjectMapper().writeValueAsString(obj)
    }
}