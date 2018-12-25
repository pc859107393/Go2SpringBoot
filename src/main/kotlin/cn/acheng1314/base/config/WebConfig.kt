package cn.acheng1314.base.config

import cn.acheng1314.base.intercept.SomeIntercept
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * 拦截器配置
 */
@Configuration
class WebConfig : WebMvcConfigurer {

    @Autowired
    lateinit var someIntercept: SomeIntercept

    override fun addInterceptors(registry: InterceptorRegistry) {
        //定义为全局的拦截器
        registry.addInterceptor(someIntercept).addPathPatterns("/**")
        super.addInterceptors(registry)
    }
}