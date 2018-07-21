package cn.acheng1314.base.redis

import cn.acheng1314.base.redis.selectKey.KeyPrefix
import cn.acheng1314.base.utils.StringUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

@Service(value = "redisService")
class RedisServiceImpl {
    @Autowired
    lateinit var jedisPool: JedisPool

    /**
     * 获取
     * @param prefix 包装的key前缀
     * @param key key
     */
    fun <T> get(prefix: KeyPrefix, key: String, clazz: Class<T>): T? {
        val jedis = jedisPool.resource
        try {
            return RedisStrAndBeanUtil.str2Bean(jedis.get(getRealKey(prefix, key)), clazz)
        } finally {
            close(jedis)
        }
    }

    /**
     * 存入
     * @param prefix 包装的key前缀
     * @param key key
     */
    fun <T> set(prefix: KeyPrefix, key: String, value: T): Boolean {
        val jedis = jedisPool.resource
        try {
            val str = RedisStrAndBeanUtil.bean2Str(value)
            if (StringUtil.isEmpty(str)) return false
            val realKey = getRealKey(prefix, key)
            if (prefix.expireTime() <= 0) jedis.set(realKey, str)
            else jedis.setex(realKey, prefix.expireTime(), str)
            return true
        } finally {
            close(jedis)
        }
    }

    /**
     * 删除
     * @param prefix 包装的key前缀
     * @param key key
     */
    fun del(prefix: KeyPrefix, key: String): Boolean {
        val jedis = jedisPool.resource
        try {
            return jedis.del(getRealKey(prefix, key)) > 0
        } finally {
            close(jedis)
        }
    }

    /**
     * 检查是否存在
     * @param prefix 包装的key前缀
     * @param key key
     */
    fun exists(prefix: KeyPrefix, key: String): Boolean {
        val jedis = jedisPool.resource
        try {
            return jedis.exists(getRealKey(prefix, key))
        } finally {
            close(jedis)
        }
    }


    /**
     * 使用前缀key，分类和避免重复
     */
    private fun getRealKey(prefix: KeyPrefix, key: String): String =
            prefix.getPrefix() + key


    private fun close(jedis: Jedis?) {
        jedis?.close()
    }

}

