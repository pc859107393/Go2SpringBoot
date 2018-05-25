package cn.acheng1314.base

import cn.acheng1314.base.config.SpringfoxJsonToGsonAdapter
import com.baomidou.mybatisplus.plugins.PaginationInterceptor
import com.google.gson.GsonBuilder
import org.mybatis.spring.annotation.MapperScan
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.interceptor.TransactionInterceptor
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.json.Json
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
class BaseApplication : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/")

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
    }

    @Bean(name = ["transactionInterceptor"])
    fun transactionInterceptor(
            platformTransactionManager: PlatformTransactionManager): TransactionInterceptor {
        val transactionInterceptor = TransactionInterceptor()
        // 事物管理器
        transactionInterceptor.transactionManager = platformTransactionManager
        val transactionAttributes = Properties()

        // 新增
        transactionAttributes.setProperty("insert*",
                "PROPAGATION_REQUIRED,-Throwable")
        transactionAttributes.setProperty("add*",
                "PROPAGATION_REQUIRED,-Throwable")
        // 修改

        transactionAttributes.setProperty("update*",
                "PROPAGATION_REQUIRED,-Throwable")
        // 删除
        transactionAttributes.setProperty("delete*",
                "PROPAGATION_REQUIRED,-Throwable")
        //查询
        transactionAttributes.setProperty("select*",
                "PROPAGATION_REQUIRED,-Throwable,readOnly")
        transactionAttributes.setProperty("find*",
                "PROPAGATION_REQUIRED,-Throwable,readOnly")
        transactionAttributes.setProperty("get*",
                "PROPAGATION_REQUIRED,-Throwable,readOnly")

        transactionInterceptor.setTransactionAttributes(transactionAttributes)
        return transactionInterceptor
    }

    //代理到ServiceImpl的Bean
    @Bean
    fun transactionAutoProxy(): BeanNameAutoProxyCreator {
        val transactionAutoProxy = BeanNameAutoProxyCreator()
        transactionAutoProxy.isProxyTargetClass = true
        transactionAutoProxy.setBeanNames("*ServiceImpl")
        transactionAutoProxy.setInterceptorNames("transactionInterceptor")
        return transactionAutoProxy
    }

    @Bean(name = ["defaultApi"])
    fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)  //Docket，Springfox的私有API设置初始化为Swagger2
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.acheng1314.base.web"))
                .paths(PathSelectors.any())
                .build()
//                .pathMapping(null)
                .apiInfo(ApiInfoBuilder()   //设置API文档的主体说明
                        .title("acheng的SpringBoot探索之路ApiDocs")
                        .description("acheng的SpringBoot探索之路")
                        .version("v1.01")
                        .termsOfServiceUrl("http://acheng1314.cn/")
                        .build())
                .groupName("默认接口")
    }
//
//    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
//        // 删除MappingJackson2HttpMessageConverter,并使用GsonHttpMessageConverter替换
//        converters.forEach { t: HttpMessageConverter<*>? ->
//            if (t is MappingJackson2HttpMessageConverter) {
//                converters.remove(t)
//                converters.add(object : GsonHttpMessageConverter() {
//                    init {
//                        //自定义Gson适配器
//                        super.setGson(GsonBuilder()
//                                .registerTypeAdapter(Json::class.java, SpringfoxJsonToGsonAdapter())
//                                .create())
//                    }
//                }) // 添加GsonHttpMessageConverter
//                return super.extendMessageConverters(converters)
//            }
//        }
//    }

    /**
     * 分页插件
     */
    @Bean
    fun paginationInterceptor() = PaginationInterceptor()

    @Bean
    fun methodValidationPostProcessor() = MethodValidationPostProcessor()

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<BaseApplication>(*args)
        }
    }

}


