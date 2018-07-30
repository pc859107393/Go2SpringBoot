package cn.acheng1314.base.utils

import cn.acheng1314.base.domain.WeToken
import cn.acheng1314.base.exception.WeixinException
import cn.acheng1314.base.utils.okHi.OkHttpUtils
import java.io.Serializable
import java.util.concurrent.atomic.AtomicBoolean

class WeApiConfig(private var appid: String, private var secret: String) : Serializable {

    private var startTime = 0L  //刷新成功的时间

    private val refreshFlag = AtomicBoolean(false)

    private var accessToken = ""

    init {
        initAccessToken(System.currentTimeMillis())
    }

    /**
     * 初始化微信AccessToken
     * @param refresh 刷新时间
     */
    @Throws(Exception::class)
    private fun initAccessToken(refresh: Long) {
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
                throw WeixinException(String.format("微信access_token刷新出错！错误信息：%s", weToken.toString()))
            }
            this.accessToken = weToken.access_token
        } catch (e: Exception) {
            startTime = oldTime
        }

        //设置为刷新完成标志
        this.refreshFlag.set(false)
    }

    /**
     *获取微信的AccessToken
     * @return 返回AccessToken
     */
    fun getAccessToken(): String {
        val now = System.currentTimeMillis()
        val timeLimit = now - this.startTime
        try {
            //compareAndSet(false, true)提供了原子性操作
            if (timeLimit > 7100000 && this.refreshFlag.compareAndSet(false, true)) {
                initAccessToken(now)
            }
        } catch (e: Exception) {
            this.refreshFlag.set(false)
            e.printStackTrace()
        } finally {
            return accessToken
        }
    }

}