package be.ict.mb.jmsasync

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.jms.annotation.EnableJms

@SpringBootApplication
@EnableJms
@EnableFeignClients
class JmsAsyncApplication

fun main(args: Array<String>) {
	runApplication<JmsAsyncApplication>(*args)
}
