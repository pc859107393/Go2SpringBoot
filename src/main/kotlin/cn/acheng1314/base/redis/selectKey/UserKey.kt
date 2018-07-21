package cn.acheng1314.base.redis.selectKey

class UserKey
private constructor(expireTime: Int, prefixStr: String) : AbstractPrefix(expireTime, prefixStr) {
    companion object {
        var getById = UserKey(0, "id")
    }
}



