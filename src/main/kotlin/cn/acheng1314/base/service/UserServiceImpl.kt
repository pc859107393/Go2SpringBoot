package cn.acheng1314.base.service

import cn.acheng1314.base.dao.UserDao
import cn.acheng1314.base.domain.bean.User
import cn.acheng1314.base.domain.wrap.ResponseWrapList
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import javafx.scene.control.Pagination
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service(value = "userService")
@CacheConfig(cacheNames = ["cache_user"])
class UserServiceImpl : ServiceImpl<UserDao, User>() {

    @Autowired
    lateinit var userDao: UserDao

    @Cacheable(sync = true)
    fun findUserByPage(pageNum: Int, pageSize: Int): ResponseWrapList<User> {
        Page<User>(pageNum.toLong(), pageSize.toLong()).setRecords(userDao.findAllByPage(Pagination(pageSize, pageNum)))
                .run { return ResponseWrapList<User>().warp(this) }
    }

    @Cacheable(sync = true)
    fun findAll() = baseMapper.selectList(null)

}