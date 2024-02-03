package be.ict.mb.jmsasync

import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component

@Component
class MessageReceiver {

    @JmsListener(destination = "numbers")
    fun receiveMessage(text: String) {
        println(text)
    }
}