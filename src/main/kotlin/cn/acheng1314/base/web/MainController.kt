package cn.acheng1314.base.web

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MainController {


    @GetMapping(value = ["/main"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @ResponseBody
    fun MainLocal(): Any = hashMapOf<Any, Any>(Pair("code", 1))

}