package com.example.lambdaconteiner.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SpringController {
    @GetMapping("")
    fun printMessage(): String {
        return "こんにちは！"
    }
}