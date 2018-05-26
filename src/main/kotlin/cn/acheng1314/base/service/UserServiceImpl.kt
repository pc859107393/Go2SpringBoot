package cn.acheng1314.base.service

import cn.acheng1314.base.dao.UserDao
import cn.acheng1314.base.domain.User
import com.baomidou.mybatisplus.plugins.pagination.Pagination
import com.baomidou.mybatisplus.service.impl.ServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service(value = "userService")
class UserServiceImpl :ServiceImpl<UserDao,User>(){
    @Autowired
    lateinit var userDao: UserDao

    fun findUserByPage(pageNum: Int, pageSize: Int): ArrayList<User> {
        return try {
            val pagination = Pagination(pageNum, pageSize)
            setTotalPage(pagination.pages)
            userDao.findAllByPage(pagination)
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    var totalPage: Long? = null
    fun setTotalPage(pages: Long) {
        this.totalPage = pages
    }
}