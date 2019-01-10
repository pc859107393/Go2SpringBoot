package cn.acheng1314.base.config

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor
import org.mybatis.spring.mapper.MapperScannerConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class MybatisPlusConfig {

    @Bean
    fun performanceInterceptor(): PerformanceInterceptor {
        val interceptor = PerformanceInterceptor()
        //格式化sql语句
        val properties = Properties()
        properties.setProperty("format", "true")
        interceptor.setProperties(properties)
        return interceptor
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    fun paginationInterceptor(): PaginationInterceptor {
        return PaginationInterceptor()
    }

    @Bean
    fun mapperScannerConfigurer() = MapperScannerConfigurer().let {
        it.setBasePackage("cn.acheng1314.base.dao")
        return@let it
    }

//    /**
//     * query support 配置
//     * @return
//     */
//    @ConfigurationProperties(prefix = "mybatis-plus-query")
//    @Bean
//    fun queryConfigProperty(): QueryConfigProperty {
//        return QueryConfigProperty()
//    }
//
//    @Bean
//    fun querySupportMethod(): QuerySupportMethod {
//        return QuerySupportMethod()
//    }
//
//    @Bean
//    fun querySupportSqlInjector(): QuerySupportSqlInjector {
//        return QuerySupportSqlInjector()
//    }
}