package cn.acheng1314.base.utils

class MapCache<T> : Cache<T> {
    private var hashMap: HashMap<Any, T?> = hashMapOf()

    override fun add(key: Any, value: T?) {
        hashMap[key] = value
    }

    override fun get(key: Any): T? = hashMap[key]

    override fun update(key: Any, value: T?) {
        hashMap[key] = value
    }

    override fun remove(key: Any) {
        hashMap.remove(key)
    }

    override fun clear() {
        hashMap.clear()
    }
}