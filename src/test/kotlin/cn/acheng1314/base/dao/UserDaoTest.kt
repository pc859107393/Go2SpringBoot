package cn.acheng1314.base.dao

import cn.acheng1314.base.BaseApplicationTests
import javafx.scene.control.Pagination
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class UserDaoTest : BaseApplicationTests() {
    @Autowired
    lateinit var userDao: UserDao

    @Test
    fun findAllTest() {
        val pagination = Pagination(1, 20)
        val list = userDao.findAllByPage(pagination)
        list?.forEach { t -> println(String.format("用户信息为：%s", t.toString())) }
    }
}