package cn.acheng1314.base

import com.baomidou.mybatisplus.plugins.PaginationInterceptor
import org.mybatis.spring.annotation.MapperScan
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.interceptor.*
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*
import kotlin.collections.HashMap


@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
@MapperScan(value = ["cn.acheng1314.base.dao"])
@Configuration
@EnableTransactionManagement
@EnableCaching
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

    fun transactionAttributeSource(): TransactionAttributeSource {
        val source = NameMatchTransactionAttributeSource()
        //只读或可写事物
        val readOnlyTx = RuleBasedTransactionAttribute()
        readOnlyTx.isReadOnly = true
        readOnlyTx.propagationBehavior = TransactionDefinition.PROPAGATION_SUPPORTS

        //可写事物
        val requiredTx = RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED
                , Collections.singletonList(RollbackRuleAttribute(Exception::class.java)))
        //使用kotlin作用域函数
        return source.also {
            it.setNameMap(HashMap<String, TransactionAttribute>().also {
                it["add*"] = requiredTx
                it["save*"] = requiredTx
                it["insert*"] = requiredTx
                it["update*"] = requiredTx
                it["delete*"] = requiredTx
                it["get*"] = readOnlyTx
                it["query*"] = readOnlyTx
                it["find*"] = readOnlyTx
            })
        }
    }

    /*事务拦截器*/
    @Bean(value = ["txInterceptor"])
    fun getTransactionInterceptor(tx: PlatformTransactionManager): TransactionInterceptor {
        return TransactionInterceptor(tx, transactionAttributeSource())
    }

    /**切面拦截规则 参数会自动从容器中注入 */
    @Bean
    fun pointcutAdvisor(txInterceptor: TransactionInterceptor): AspectJExpressionPointcutAdvisor {
        val pointcutAdvisor = AspectJExpressionPointcutAdvisor()
        pointcutAdvisor.advice = txInterceptor
        pointcutAdvisor.expression = "execution (* cn.acheng1314.base.service.*ServiceImpl.*(..))"
        return pointcutAdvisor
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


