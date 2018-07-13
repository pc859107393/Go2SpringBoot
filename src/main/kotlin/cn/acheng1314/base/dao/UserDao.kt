package cn.acheng1314.base.dao

import cn.acheng1314.base.domain.User
import com.baomidou.mybatisplus.mapper.BaseMapper
import com.baomidou.mybatisplus.plugins.pagination.Pagination
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository(value = "userDao")
interface UserDao : BaseMapper<User> {

    @Select("SELECT * FROM `cc_user` ORDER BY concat(`used`,`id`) DESC")
    fun findAllByPage(pagination: Pagination): List<User>?
}