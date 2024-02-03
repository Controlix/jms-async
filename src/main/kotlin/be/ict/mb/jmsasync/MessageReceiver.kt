package be.ict.mb.jmsasync

import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class MessageReceiver {

    @JmsListener(destination = "numbers")
    fun receiveMessage(text: String) {
        LoggerFactory.getLogger(MessageReceiver::class.java).info("Start processing message {}", text)
        TimeUnit.MILLISECONDS.sleep(200)
        LoggerFactory.getLogger(MessageReceiver::class.java).info("Finished processing message {}", text)
    }
}