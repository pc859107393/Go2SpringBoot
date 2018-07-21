package cn.acheng1314.base.redis.selectKey

private class UserKey(expireTime: Int, prefixStr: String) : AbstractPrefix(expireTime, prefixStr) {

    var getById = UserKey(0, "id")
}

