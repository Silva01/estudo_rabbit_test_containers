package br.net.silva.daniel.api.queue.test.rabbitmq.controller

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@WebMvcTest
@Tag("integration")
class MainControllerTest(@Autowired val mockMvc: MockMvc) {

    @Test
    fun `should return a message`() {
        mockMvc.perform(get("/api/"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(""))
    }

    @Test
    fun `should return status 200`() {
        mockMvc.perform(get("/api/"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}