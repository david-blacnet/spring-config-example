package com.davidhuang.controllers

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.NoHandlerFoundException

@RefreshScope
@RestController
class MessageRestController(
    @Value("\${message.get.enabled}") val getEnabled: Boolean,
    @Value("\${message:Hello default}") val message: String
) {
    @GetMapping("/message")
    fun get(): String {
        if (!getEnabled) {
            throw NoHandlerFoundException("GET", "/message", null)
        }
        return message
    }
}