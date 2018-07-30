package cn.acheng1314.base.service

import cn.acheng1314.base.dao.UserDao
import cn.acheng1314.base.domain.User
import cn.acheng1314.base.domain.wrap.ResponseWrapList
import com.baomidou.mybatisplus.plugins.Page
import com.baomidou.mybatisplus.service.impl.ServiceImpl
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
        Page<User>(pageNum, pageSize)
                .run { this.setRecords(userDao.findAllByPage(this)) }
                .run { return ResponseWrapList<User>().warp(this) }
    }

    @Cacheable(sync = true)
    fun findAll() = baseMapper.selectList(null)

}