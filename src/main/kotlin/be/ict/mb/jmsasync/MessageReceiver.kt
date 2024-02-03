package be.ict.mb.jmsasync

import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class MessageReceiver(private val resourceClient: ResourceClient) {

    /**
     * Vaststelling:
     * - Als de queue leeg is wanneer je opstart, dan maakt spring 5 receivers aan
     * - Als de queue niet leeg is als je opstart, dan is het aantal receivers onbepaald. Soms is er maar 1, soms 2, soms 4, etc.
     * - Als de queue niet leeg is, maar je pauseert de queue alvorens je opstart, en vervolgens resume je de queue nadat je opgestart bent, dan maakt spring 5 receivers aan.
     */
    @JmsListener(destination = "numbers", concurrency = "5-5")
    fun receiveMessage(text: String) {
        LoggerFactory.getLogger(MessageReceiver::class.java).info("Start processing message {}", text)
        val statsForMessage = resourceClient.findStatsForMessage("Kirk")
        LoggerFactory.getLogger(MessageReceiver::class.java).info("Got stats for message {}: {}", "Kirk", statsForMessage)
        TimeUnit.MILLISECONDS.sleep(200)
        LoggerFactory.getLogger(MessageReceiver::class.java).info("Finished processing message {}", text)
    }
}

// 2024-02-03T12:40:40.927+01:00  INFO 111984 --- [ntContainer#0-3] be.ict.mb.jmsasync.MessageReceiver       : Start processing message hello
// 2024-02-03T12:40:45.092+01:00  INFO 111984 --- [ntContainer#0-4] be.ict.mb.jmsasync.MessageReceiver       : Finished processing message hello

// 10 messages - no concurrency
// 2024-02-03T19:53:40.328+01:00  INFO 122472 --- [ntContainer#0-1] be.ict.mb.jmsasync.MessageReceiver       : Start processing message hello
// 2024-02-03T19:53:48.758+01:00  INFO 122472 --- [ntContainer#0-1] be.ict.mb.jmsasync.MessageReceiver       : Finished processing message hello
// => 8.430s

// 10 messages - concurrency = 5
// 2024-02-03T19:57:13.918+01:00  INFO 122687 --- [ntContainer#0-2] be.ict.mb.jmsasync.MessageReceiver       : Start processing message hello
// 2024-02-03T19:57:16.492+01:00  INFO 122687 --- [ntContainer#0-2] be.ict.mb.jmsasync.MessageReceiver       : Finished processing message hello
// => 2.574s

// 10 messages - concurrency = 5, rss bulkhead = 2
// 2024-02-03T20:01:18.114+01:00  INFO 123200 --- [ntContainer#0-2] be.ict.mb.jmsasync.MessageReceiver       : Start processing message hello
// 2024-02-03T20:01:23.263+01:00  INFO 123200 --- [ntContainer#0-5] be.ict.mb.jmsasync.MessageReceiver       : Finished processing message hello
// => 5.149s
