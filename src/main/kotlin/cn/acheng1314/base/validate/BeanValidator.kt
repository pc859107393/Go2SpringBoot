package cn.acheng1314.base.validate

import com.google.common.collect.Iterables.getFirst
import javax.validation.Validation
import javax.validation.ValidationException

object BeanValidator {
    fun <T> validate(`object`: T) {
        //获得验证器
        val validator = Validation.buildDefaultValidatorFactory().validator
        //执行验证
        val constraintViolations = validator.validate(`object`)
        //如果有验证信息，则将第一个取出来包装成异常返回
        val constraintViolation = getFirst(constraintViolations, null)
        if (constraintViolation != null) {
            throw ValidationException(constraintViolation.message)
        }
    }

}