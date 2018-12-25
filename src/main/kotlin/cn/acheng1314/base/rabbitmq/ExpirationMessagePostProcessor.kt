//package cn.acheng1314.base.rabbitmq
//
//
//import org.springframework.amqp.AmqpException
//import org.springframework.amqp.core.Message
//import org.springframework.amqp.core.MessagePostProcessor
//
///**
// * 设置队列消息的过期时间
// */
//class ExpirationMessagePostProcessor
///**
// *
// * @param ttl
// */
//(private val ttl: Long? // 毫秒
//) : MessagePostProcessor {
//
//    @Throws(AmqpException::class)
//    override fun postProcessMessage(message: Message): Message {
//        message.messageProperties.expiration = ttl!!.toString() // 设置per-message的失效时间
//        return message
//    }
//}
