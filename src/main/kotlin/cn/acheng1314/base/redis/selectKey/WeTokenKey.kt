package cn.acheng1314.base.redis.selectKey

class WeTokenKey
private constructor(expireTime: Int, prefixStr: String) : AbstractPrefix(expireTime, prefixStr) {
    companion object {
        val tokenKey = WeTokenKey(1000 * 60 * 4, "weToken")
    }
}