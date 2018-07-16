package cn.acheng1314.base.web

import cn.acheng1314.base.domain.User
import cn.acheng1314.base.exception.ApiException
import cn.acheng1314.base.service.UserServiceImpl
import cn.acheng1314.base.utils.GsonUtil
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
class MainController {


    @GetMapping(value = ["/"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @ResponseBody
    @ApiOperation(value = "User输出测试", notes = "用户查询", response = User::class)
    fun MainLocal(): Any = User("程", "18976962315", "123456", "吹牛", Date())

    @GetMapping(value = ["/test"], produces = [MediaType.TEXT_HTML_VALUE])
    fun getTest(map: ModelMap): String {
        map["test"] = MainLocal()
        return "test1"
    }

    @PostMapping(value = ["/json"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @ResponseBody
    @ApiOperation(value = "返回提交的User", notes = "返回提交的User", response = User::class)
    fun getJson(@RequestBody user: User): Any {
        println(String.format("用户信息：%s", user.toString()))
        return GsonUtil.toJson(user)
    }

    @Autowired
    lateinit var userService: UserServiceImpl

    @GetMapping(value = ["/users"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @ResponseBody
    @ApiOperation(value = "获取用户集合", notes = "用户列表查询", responseContainer = "List", response = User::class)
    fun findUserPage(@RequestParam(value = "pageNum", required = false, defaultValue = "1") pageNum: Int,
                     @RequestParam(value = "pageSize", required = false, defaultValue = "15") pageSize: Int): Any = userService.findUserByPage(pageNum, pageSize)

    @GetMapping(value = ["/allUser"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @ResponseBody
    @ApiOperation(value = "获取所有用户集合", notes = "所有用户列表查询", responseContainer = "List", response = User::class)
    fun findAllUser(): Any = userService.findAll()

    @GetMapping(value = ["lowb"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    @ResponseBody
    @ApiOperation(value = "异常测试", notes = "异常测试")
    @Throws(ApiException::class)
    fun testException(): Any = throw ApiException("test Api Exception")


}