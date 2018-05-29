package cn.acheng1314.base.service

import cn.acheng1314.base.BaseApplicationTests
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired


class UserServiceImplTest : BaseApplicationTests() {
    @Autowired
    private lateinit var userService: UserServiceImpl

    @Test
    fun addUserTest() {
        userService.addUser()
    }
}