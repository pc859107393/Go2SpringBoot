//package cn.acheng1314.base.rabbitmq
//
//
//import org.springframework.amqp.core.*
//import org.springframework.amqp.rabbit.connection.ConnectionFactory
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//import java.util.HashMap
//
//@Configuration
//class QueueConfig {
//
//    /**
//     * 创建DLX exchange
//     *
//     * @return
//     */
//    @Bean
//    fun delayExchange(): DirectExchange {
//        return DirectExchange(DELAY_EXCHANGE_NAME)
//    }
//
//    /**
//     * 创建per_queue_ttl_exchange
//     *
//     * @return
//     */
//    @Bean
//    fun perQueueTTLExchange(): DirectExchange {
//        return DirectExchange(PER_QUEUE_TTL_EXCHANGE_NAME)
//    }
//
//    /**
//     * 创建delay_queue_per_message_ttl队列
//     *
//     * @return
//     */
//    @Bean
//    fun delayQueuePerMessageTTL(): Queue {
//
//        val params = HashMap<String, Any>()
//        params["x-dead-letter-exchange"] = DELAY_EXCHANGE_NAME
//        params["x-dead-letter-routing-key"] = DELAY_PROCESS_QUEUE_NAME
//
//        return Queue(DELAY_QUEUE_PER_MESSAGE_TTL_NAME, true, false, false, params)
//
//    }
//
//    /**
//     * 创建delay_queue_per_queue_ttl队列
//     *
//     * @return
//     */
//    @Bean
//    fun delayQueuePerQueueTTL(): Queue {
//        val params = HashMap<String, Any>()
//        params["x-dead-letter-exchange"] = DELAY_EXCHANGE_NAME
//        params["x-dead-letter-routing-key"] = DELAY_PROCESS_QUEUE_NAME
//        params["x-message-ttl"] = QUEUE_EXPIRATION
//
//        return Queue(DELAY_QUEUE_PER_QUEUE_TTL_NAME, true, false, false, params)
//    }
//
//    /**
//     * 创建delay_process_queue队列，也就是实际消费队列
//     *
//     * @return
//     */
//    @Bean
//    fun delayProcessQueue(): Queue {
//
//        return Queue(DELAY_PROCESS_QUEUE_NAME, true, false, false)
//    }
//
//    /**
//     * 将DLX绑定到实际消费队列
//     *
//     * @param delayProcessQueue
//     * @param delayExchange
//     * @return
//     */
//    @Bean
//    fun dlxBinding(delayProcessQueue: Queue, delayExchange: DirectExchange): Binding {
//        return BindingBuilder.bind(delayProcessQueue)
//                .to(delayExchange)
//                .with(DELAY_PROCESS_QUEUE_NAME)
//    }
//
//    /**
//     * 将per_queue_ttl_exchange绑定到delay_queue_per_queue_ttl队列
//     *
//     * @param delayQueuePerQueueTTL
//     * @param perQueueTTLExchange
//     * @return
//     */
//    @Bean
//    fun queueTTLBinding(delayQueuePerQueueTTL: Queue, perQueueTTLExchange: DirectExchange): Binding {
//        return BindingBuilder.bind(delayQueuePerQueueTTL)
//                .to(perQueueTTLExchange)
//                .with(DELAY_QUEUE_PER_QUEUE_TTL_NAME)
//    }
//
//    /**
//     * 定义delay_process_queue队列的Listener Container
//     *
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    fun processContainer(connectionFactory: ConnectionFactory, processReceiver: ProcessReceiver): SimpleMessageListenerContainer {
//        val container = SimpleMessageListenerContainer()
//        container.connectionFactory = connectionFactory
//        container.setQueueNames(DELAY_PROCESS_QUEUE_NAME) // 监听delay_process_queue
//        container.setMessageListener(MessageListenerAdapter(processReceiver))
//        container.acknowledgeMode = AcknowledgeMode.NONE             //开启手动 ack
//        return container
//    }
//
//    companion object {
//
//        /**
//         * 发送到该队列的message会在一段时间后过期进入到delay_process_queue
//         * 每个message可以控制自己的失效时间
//         */
//        const val DELAY_QUEUE_PER_MESSAGE_TTL_NAME = "delay_queue_per_message_ttl_test001"
//
//        /**
//         * 发送到该队列的message会在一段时间后过期进入到delay_process_queue
//         * 队列里所有的message都有统一的失效时间
//         */
//        const val DELAY_QUEUE_PER_QUEUE_TTL_NAME = "delay_queue_per_queue_ttl_test001"
//        const val QUEUE_EXPIRATION = 3000
//
//        /**
//         * message失效后进入的队列，也就是实际的消费队列
//         */
//        const val DELAY_PROCESS_QUEUE_NAME = "delay_process_queue_test001"
//
//        /**
//         * DLX
//         */
//        const val DELAY_EXCHANGE_NAME = "delay_exchange_test001"
//
//        /**
//         * 路由到delay_queue_per_queue_ttl的exchange
//         */
//        const val PER_QUEUE_TTL_EXCHANGE_NAME = "per_queue_ttl_exchange_test001"
//    }
//
//}