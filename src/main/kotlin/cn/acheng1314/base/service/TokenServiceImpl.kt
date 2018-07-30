package cn.acheng1314.base.service

import cn.acheng1314.base.domain.WeToken
import cn.acheng1314.base.redis.RedisServiceImpl
import cn.acheng1314.base.redis.selectKey.WeTokenKey
import cn.acheng1314.base.utils.StringUtil
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service("tokenService")
class TokenServiceImpl {

    lateinit var redisService: RedisServiceImpl

    @Scheduled(fixedRate = 1000 * 60 * 3)
    fun initAccessToken() {
        //32位的uuid作为accessToken
        val weToken = WeToken()
        weToken.access_token = StringUtil.uuid32
        weToken.expires_in = 1000 * 60 * 3
//        redisService.set(WeTokenKey.tokenKey,)

    }
}