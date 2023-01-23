package br.net.silva.daniel.api.queue.test.rabbitmq.controller

import br.net.silva.daniel.api.queue.test.rabbitmq.service.QueueService
import br.net.silva.daniel.api.queue.test.rabbitmq.util.JsonConverter
import br.net.silva.daniel.api.queue.test.rabbitmq.value_object.PeopleMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class MainController(@Autowired private val service: QueueService) {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    fun index() {
        val message = PeopleMessage("Hello", "Daniel", 33)
        service.send(JsonConverter.convert(message))
    }
}