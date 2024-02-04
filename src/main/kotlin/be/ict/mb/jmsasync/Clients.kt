package be.ict.mb.jmsasync

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "greetings")
interface GreetingsClient {

    @GetMapping("/just-wait/greetings/hello")
    fun hello(): String
}

@FeignClient(name = "more-greetings")
interface MoreGreetingsClient {

    @GetMapping("/just-wait/greetings/hello")
    fun hello(): String
}