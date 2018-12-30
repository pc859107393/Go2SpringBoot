package cn.acheng1314.base.validate.annotation

import cn.acheng1314.base.validate.IsMobileValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION
        , AnnotationTarget.PROPERTY_GETTER
        , AnnotationTarget.PROPERTY_SETTER
        , AnnotationTarget.FIELD
        , AnnotationTarget.ANNOTATION_CLASS
        , AnnotationTarget.CONSTRUCTOR
        , AnnotationTarget.VALUE_PARAMETER)
@Retention()
@MustBeDocumented
@Constraint(validatedBy = [(IsMobileValidator::class)])//引进校验器
annotation class IsMobile(val required: Boolean = true//默认不能为空
                          , val message: String = "手机号码格式错误"//校验不通过输出信息
                          , val groups: Array<KClass<*>> = [], val payload: Array<KClass<out Payload>> = [])
