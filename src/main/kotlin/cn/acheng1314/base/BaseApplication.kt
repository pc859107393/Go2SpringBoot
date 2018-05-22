package cn.acheng1314.base

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
@MapperScan("cn.acheng1314.base.dao")
class BaseApplication

fun main(args: Array<String>) {
    runApplication<BaseApplication>(*args)
}
