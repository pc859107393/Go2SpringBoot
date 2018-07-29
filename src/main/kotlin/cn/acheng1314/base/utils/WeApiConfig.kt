package cn.acheng1314.base.utils

import cn.acheng1314.base.domain.WeToken
import cn.acheng1314.base.exception.WeixinException
import cn.acheng1314.base.utils.okHi.OkHttpUtils
import java.io.Serializable
import java.util.concurrent.atomic.AtomicBoolean

class WeApiConfig(private var appid: String, private var secret: String) : Serializable {

    private var startTime = 0L  //刷新成功的时间

    private val refreshFlag = AtomicBoolean(false)

    init {
        initAccessToken(System.currentTimeMillis())
    }

    @Throws(Exception::class)
    fun initAccessToken(refresh: Long) {
        //记住初始时间和刷新时间
        val oldTime = startTime
        this.startTime = refresh
        try {
            val response = OkHttpUtils.get<String>("https://api.weixin.qq.com/cgi-bin/token")
                    .params("grant_type", "client_credential")
                    .params("appid", appid)
                    .params("secret", secret)
                    .execute()
            val weToken = GsonUtil.toBean(response.body().toString(), WeToken::class.java)
            if (StringUtil.isEmpty(weToken.access_token)) {
                throw WeixinException("微信access_token刷新出错！")
            }
        } catch (e: Exception) {
            startTime = oldTime
        }

        //设置为刷新完成标志
        this.refreshFlag.set(false)
    }

}