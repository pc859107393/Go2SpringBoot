package cn.acheng1314.base.redis

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "redis")
class RedisConfig {
    var host: String = "localhost"
    var port: Int = -1
    var timeout = 2000
    var password = ""
    var poolMaxTotal = 100
    var poolMaxIdle = 8
    var poolMinIdle = 0
    var poolMaxWait:Long = -1

    override fun toString(): String {
        return "RedisConfig(host='$host', prot=$port, timeout=$timeout, password='$password', poolMaxTotal=$poolMaxTotal, poolMaxIdle=$poolMaxIdle, poolMinIdle=$poolMinIdle, poolMaxWait=$poolMaxWait)"
    }


}