package cn.acheng1314.base

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@MapperScan(basePackages = ["cn.acheng1314.base.dao"])
@EnableCaching
//@EnableScheduling
class BaseApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<BaseApplication>(*args)
        }
    }

}