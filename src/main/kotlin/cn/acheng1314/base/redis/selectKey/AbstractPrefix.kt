package cn.acheng1314.base.redis.selectKey

abstract class AbstractPrefix : KeyPrefix {
    var expireTime = 3600 * 24 * 7
    var prefixStr = ""

    constructor(expireTime: Int, prefixStr: String) {
        this.expireTime = expireTime
        this.prefixStr = prefixStr
    }

    //永不过期
    constructor(prefixStr: String) : this(0, prefixStr)

    override fun expireTime(): Int = expireTime

    override fun getPrefix(): String = String.format("%s:%s", javaClass.simpleName, prefixStr)
}