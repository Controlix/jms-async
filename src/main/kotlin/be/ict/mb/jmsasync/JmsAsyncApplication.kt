package be.ict.mb.jmsasync

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jms.annotation.EnableJms

@SpringBootApplication
@EnableJms
class JmsAsyncApplication

fun main(args: Array<String>) {
	runApplication<JmsAsyncApplication>(*args)
}
