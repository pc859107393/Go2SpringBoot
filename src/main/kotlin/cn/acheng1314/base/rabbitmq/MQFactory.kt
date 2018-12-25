//package cn.acheng1314.base.rabbitmq
//
//
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
//import org.springframework.amqp.rabbit.connection.ConnectionFactory
//import org.springframework.amqp.rabbit.core.RabbitTemplate
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.context.annotation.PropertySource
//
//@Configuration
//@PropertySource("classpath:/config/rabbitmq.properties")
//class MQFactory {
//
//    @Value("\${rabbitmq.host}")
//    private val raHost = "localhost"
//
//    @Value("\${rabbit.user}")
//    private val user = "guest"
//
//    @Value("\${rabbit.pwd}")
//    private val pwd = "guest"
//
//    @Value("\${rabbit.port}")
//    private val host = 5672
//
//    @Value(value = "\${rabbit.used}")
//    private val used = false
//
//    /**
//     * 更新：添加开启生产者消息确认机制
//     *
//     * @return
//     */
//    @Bean
//    fun connectionFactory(): ConnectionFactory? {
//        if (!used) return null
//        val connectionFactory = CachingConnectionFactory(raHost)
//        connectionFactory.username = user
//        connectionFactory.setPassword(pwd)
//        connectionFactory.port = host
//        connectionFactory.isPublisherConfirms = true
//        connectionFactory.isPublisherReturns = true
//        return connectionFactory
//    }
//
//    //        <bean class="org.springframework.amqp.rabbit.core.RabbitTemplate">
//    //        <property name="connectionFactory" ref="rabbitConnectionFactory"/>
//    //        <property name="messageConverter" ref="converter"/>
//    //    </bean>
//    //
//    //    <bean id="converter" class="org.springframework.amqp.support.converter.SimpleMessageConverter">
//    //    </bean>
//
//
//    @Bean
//    fun rabbitTemplate(connectionFactory: ConnectionFactory?) = if (connectionFactory == null) null else RabbitTemplate(connectionFactory)
//
//}