package cn.acheng1314.base.redis

import cn.acheng1314.base.utils.GsonUtil
import cn.acheng1314.base.utils.StringUtil

object RedisStrAndBeanUtil {

    @Suppress("UNCHECKED_CAST")
    fun <T> str2Bean(str: String, clazz: Class<T>?): T? {
        if (StringUtil.isEmpty(str)) return null

        return if (clazz == Int::class.java || clazz == Int::class.javaPrimitiveType) {
            Integer.valueOf(str) as T
        } else if (clazz == Long::class.javaPrimitiveType || clazz == Long::class.java) {
            java.lang.Long.valueOf(str) as T
        } else if (clazz == String::class.java) {
            str as T?
        } else {
            GsonUtil.toBean(str, clazz!!)
        }
    }


    fun bean2Str(value: Any?): String? {
        if (value == null) {
            return null
        }
        val clazz = value.javaClass
        return if (clazz == Int::class.javaPrimitiveType || clazz == Int::class.java) {
            value.toString()
        } else if (clazz == Long::class.javaPrimitiveType || clazz == Long::class.java) {
            value.toString()
        } else if (clazz == String::class.java) {
            value as String?
        } else {
            GsonUtil.toJson(value)
        }

    }


}