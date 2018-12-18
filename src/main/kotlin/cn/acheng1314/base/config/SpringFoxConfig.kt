package cn.acheng1314.base.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableWebMvc
@EnableSwagger2
class SpringFoxConfig : WebMvcConfigurer {

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


}