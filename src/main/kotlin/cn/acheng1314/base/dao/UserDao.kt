package cn.acheng1314.base.dao

import cn.acheng1314.base.domain.bean.User
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import javafx.scene.control.Pagination
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository(value = "userDao")
//@Mapper
interface UserDao : BaseMapper<User> {

    @Select("SELECT * FROM `cc_user` ORDER BY concat(`used`,`id`) DESC")
    fun findAllByPage(pagination: Pagination): List<User>?
}