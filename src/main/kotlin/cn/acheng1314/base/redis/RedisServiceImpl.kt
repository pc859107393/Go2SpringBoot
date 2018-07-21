package cn.acheng1314.base.redis

import cn.acheng1314.base.redis.selectKey.KeyPrefix
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import redis.clients.jedis.JedisPool

@Service
class RedisServiceImpl {
    @Autowired
    lateinit var jedisPool: JedisPool

    fun <T> get(prefix: KeyPrefix, key: String, clazz: Class<T>): T {

    }


}

