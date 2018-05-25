package cn.acheng1314.base.web

import cn.acheng1314.base.domain.User
import cn.acheng1314.base.utils.GsonUtil
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*

@Controller
class MainController {


    @GetMapping(value = ["/"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @ResponseBody
    fun MainLocal(): Any = GsonUtil.toJson(User("程", "18976962315", "123456", "吹牛逼", Date()))

    @PostMapping(value = ["/json"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @ResponseBody
    fun getJson(@RequestBody user: User): Any {
        println(String.format("用户信息：%s", user.toString()))
        return GsonUtil.toJson(user)
    }

}