package cn.acheng1314.base.redis.selectKey

/**
 * redis数据库操作key的前缀
 */
interface KeyPrefix {
    //有效期
    fun expireTime():Int

    //获取前缀
    fun getPrefix():String
}