package cn.acheng1314.base.redis

import cn.acheng1314.base.domain.User
import cn.acheng1314.base.utils.StringUtil

class RedisStrBeanTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(RedisStrAndBeanUtil.str2Bean("2", Int::class.java))
            println(RedisStrAndBeanUtil.str2Bean("{\"id\":null,\"name\":\"程\",\"loginName\":\"18976962315\",\"password\":\"123456\",\"duty\":\"吹牛\",\"createDate\":\"2018-07-22 02:03:06\",\"used\":false}", User::class.java))
            println(RedisStrAndBeanUtil.bean2Str("2"))
            println(RedisStrAndBeanUtil.bean2Str(User()))
            println("手机号验证：18976962315\t" + StringUtil.isPhone("18976962315"))
        }
    }
}