package be.ict.mb.jmsasync

import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component

@Component
class MessageReceiver(private val greetingsClient: GreetingsClient, private val moreGreetingsClient: MoreGreetingsClient) {

    /**
     * Vaststelling:
     * - Als de queue leeg is wanneer je opstart, dan maakt spring 5 receivers aan
     * - Als de queue niet leeg is als je opstart, dan is het aantal receivers onbepaald. Soms is er maar 1, soms 2, soms 4, etc.
     * - Als de queue niet leeg is, maar je pauseert de queue alvorens je opstart, en vervolgens resume je de queue nadat je opgestart bent, dan maakt spring 5 receivers aan.
     */
    @JmsListener(destination = "numbers", concurrency = "10-10")
    fun receiveMessage(text: String) {
        LoggerFactory.getLogger(MessageReceiver::class.java).info("Start processing message {}", text)
        greetingsClient.hello()
        moreGreetingsClient.hello()
        LoggerFactory.getLogger(MessageReceiver::class.java).info("Finished processing message {}", text)
    }
}

// 1000 messages - no concurrency
// 2024-02-04T12:44:53.133+01:00  INFO 132660 --- [ntContainer#0-1] be.ict.mb.jmsasync.MessageReceiver       : Start processing message hello
// 2024-02-04T12:55:03.943+01:00  INFO 132660 --- [ntContainer#0-1] be.ict.mb.jmsasync.MessageReceiver       : Finished processing message hello
// => 10min 10.810s ~98 msg/min

// 1000 messages - concurrency = 5
// 2024-02-04T13:14:52.953+01:00  INFO 133976 --- [ntContainer#0-5] be.ict.mb.jmsasync.MessageReceiver       : Start processing message hello
// 2024-02-04T13:17:00.010+01:00  INFO 133976 --- [ntContainer#0-4] be.ict.mb.jmsasync.MessageReceiver       : Finished processing message hello
// => 2min 7.073s ~470 msg/min

// 1000 messages - concurrency = 10
// 2024-02-04T13:19:35.933+01:00  INFO 134405 --- [ntContainer#0-5] be.ict.mb.jmsasync.MessageReceiver       : Start processing message hello
// 2024-02-04T13:20:50.102+01:00  INFO 134405 --- [ntContainer#0-3] be.ict.mb.jmsasync.MessageReceiver       : Finished processing message hello
// => 1min 14.169s ~810 msg/min
