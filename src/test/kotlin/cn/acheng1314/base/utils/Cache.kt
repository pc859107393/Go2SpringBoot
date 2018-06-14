package cn.acheng1314.base.utils

import org.springframework.lang.Nullable

/**
 * 简单缓存接口
 * @author cheng
 */
interface Cache<T> {
    fun add(key: Any, @Nullable value: T?)

    fun get(key: Any): T?

    fun remove(key: Any)

    fun update(key: Any, @Nullable value: T?)

    fun clear()
}