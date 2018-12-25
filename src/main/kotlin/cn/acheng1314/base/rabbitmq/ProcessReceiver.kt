//package cn.acheng1314.base.rabbitmq
//
//
//import com.rabbitmq.client.Channel
//import org.slf4j.LoggerFactory
//import org.springframework.amqp.core.Message
//import org.springframework.amqp.rabbit.annotation.RabbitListener
//import org.springframework.amqp.rabbit.core.RabbitTemplate
//import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Component
//import java.util.*
//import java.util.concurrent.CountDownLatch
//
//@Component
//class ProcessReceiver : ChannelAwareMessageListener {
//
//    @Autowired
//    private val rabbitTemplate: RabbitTemplate? = null
//
//    @Throws(Exception::class)
//    override fun onMessage(message: Message, channel: Channel) {
//        try {
//            //            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            processMessage(message)
//        } catch (e: Exception) {
//            // 如果发生了异常，则将该消息重定向到缓冲队列，会在一定延迟之后自动重做
//            channel.basicPublish(QueueConfig.PER_QUEUE_TTL_EXCHANGE_NAME, QueueConfig.DELAY_QUEUE_PER_QUEUE_TTL_NAME, null,
//                    "The failed message will auto retry after a certain delay".toByteArray())
//        }
//
//        if (latch != null) {
//            latch!!.countDown()
//        }
//    }
//
//    /**
//     * 模拟消息处理。如果当消息内容为FAIL_MESSAGE的话，则需要抛出异常
//     *
//     * @param message
//     * @throws Exception
//     */
//    @Throws(Exception::class)
//    fun processMessage(message: Message) {
//        val realMessage = String(message.body)
//        rabbitTemplate!!.convertAndSend(realMessage)
//        logger.info("Received <$realMessage>")
//        if (realMessage == FAIL_MESSAGE) {
//            throw Exception("Some exception happened")
//        }
//    }
//
//    @RabbitListener(queues = [QueueConfig.DELAY_PROCESS_QUEUE_NAME])
//    fun recived(message: Message) {
//        val realMessage = String(message.body)
//        println(Date().time.toString() + "==================快速对账：" + realMessage)
//
//
//    }
//
//    companion object {
//        var latch: CountDownLatch? = null
//        private val logger = LoggerFactory.getLogger(ProcessReceiver::class.java)
//
//        val FAIL_MESSAGE = "This message will fail"
//    }
//
//}
