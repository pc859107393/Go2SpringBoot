package cn.acheng1314.base.redis

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig

@Service
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
        return JedisPool(config
                , redisConfig.host
                , redisConfig.port
                , redisConfig.timeout
                , redisConfig.password
                , 0)
    }

}