package cn.acheng1314.base.redis.selectKey

class WeTokenKey private constructor(expireTime: Int, prefixStr: String) : AbstractPrefix(expireTime, prefixStr) {
    companion object {
        var getTokenKey = WeTokenKey(60 * 4, "weToken")
    }
}
