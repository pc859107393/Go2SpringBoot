package cn.acheng1314.base.service

import cn.acheng1314.base.domain.WeToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service("tokenService")
class TokenServiceImpl {

    var lastTime = 0L

    /**
     * 每隔三秒执行任务，产生一个token
     */
    @Scheduled(fixedRate = 1000 * 60 * 3)
    fun initAccessToken() {
        lastTime = System.currentTimeMillis()

        val weToken = WeToken()
        weToken.access_token = lastTime.toString()
        weToken.expires_in = 1000 * 60 * 4

    }
}