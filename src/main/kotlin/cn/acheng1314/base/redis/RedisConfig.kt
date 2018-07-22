package cn.acheng1314.base.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "redis")
class RedisConfig {
    var host: String = "127.0.0.1"
    var port: Int = -1
    var timeout = 10 * 1000
    var password: String? = null
    var poolMaxTotal = 1000
    var poolMaxIdle = 500
    var poolMinIdle = 8
    var poolMaxWait: Long = 500 * 1000

    override fun toString(): String {
        return "RedisConfig(host='$host', prot=$port, timeout=$timeout, password='$password', poolMaxTotal=$poolMaxTotal, poolMaxIdle=$poolMaxIdle, poolMinIdle=$poolMinIdle, poolMaxWait=$poolMaxWait)"
    }


}