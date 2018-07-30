package cn.acheng1314.base.utils

/**
 * 单例的WeXinToken获取
 */
class WeSingletonToken private constructor() {

    lateinit var weApiConfig: WeApiConfig

    @Volatile
    private var single: WeSingletonToken? = null

    fun getInstance(appId: String, secret: String): WeSingletonToken {
        if (single == null) {
            synchronized(WeSingletonToken::class) {
                if (single == null) {
                    single = WeSingletonToken()
                    weApiConfig = WeApiConfig(appId, secret)
                }
            }
        }
        return single!!
    }

    fun getAccessToken(): String = weApiConfig.getAccessToken()
}