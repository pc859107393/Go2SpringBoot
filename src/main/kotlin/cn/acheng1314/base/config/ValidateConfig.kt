package cn.acheng1314.base.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor

@Configuration
class ValidateConfig {

    @Bean
    fun methodValidationPostProcessor() = MethodValidationPostProcessor()

}