package be.ict.mb.jmsasync

import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class MessageReceiver {

    /**
     * Vaststelling:
     * - Als de queue leeg is wanneer je opstart, dan maakt spring 5 receivers aan
     * - Als de queue niet leeg is als je opstart, dan is het aantal receivers onbepaald. Soms is er maar 1, soms 2, soms 4, etc.
     * - Als de queue niet leeg is, maar je pauseert de queue alvorens je opstart, en vervolgens resume je de queue nadat je opgestart bent, dan maakt spring 5 receivers aan.
     */
    @JmsListener(destination = "numbers", concurrency = "5-5")
    fun receiveMessage(text: String) {
        LoggerFactory.getLogger(MessageReceiver::class.java).info("Start processing message {}", text)
        TimeUnit.MILLISECONDS.sleep(200)
        LoggerFactory.getLogger(MessageReceiver::class.java).info("Finished processing message {}", text)
    }
}

// 2024-02-03T12:40:40.927+01:00  INFO 111984 --- [ntContainer#0-3] be.ict.mb.jmsasync.MessageReceiver       : Start processing message hello
// 2024-02-03T12:40:45.092+01:00  INFO 111984 --- [ntContainer#0-4] be.ict.mb.jmsasync.MessageReceiver       : Finished processing message hello
