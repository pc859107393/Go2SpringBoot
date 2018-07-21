package cn.acheng1314.base.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig

@Component
class RedisFactory {
    @Autowired
    lateinit var redisConfig: RedisConfig

    @Bean
    fun JedisPoolFactory(): JedisPool {
        println("Redis配置：" + redisConfig.toString())
        val config = JedisPoolConfig()
        config.maxIdle = redisConfig.poolMaxIdle
        config.minIdle = redisConfig.poolMinIdle
        config.maxTotal = redisConfig.poolMaxTotal
        config.maxWaitMillis = redisConfig.poolMaxWait
        val jedisPool = JedisPool(config
                , redisConfig.host
                , redisConfig.port
                , redisConfig.timeout
                , if (null != redisConfig.password) redisConfig.password else null
                , 1)
        return jedisPool
    }

}