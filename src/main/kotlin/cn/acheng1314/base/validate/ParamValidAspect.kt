package cn.acheng1314.base.validate

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Aspect
@Component
class ParamValidAspect {

    private var logger = LoggerFactory.getLogger(this.javaClass)

    //"&& @within(org.springframework.stereotype.Service)"
    @Pointcut(value = "@annotation(cn.acheng1314.base.validate.annotation.BeanValid) ")
    fun checkParam() {
    }

    @Around(value = "checkParam()")
    fun doBefore(joinPoint: ProceedingJoinPoint): Any = checkArgs(joinPoint)

//    /**
//     * 在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
//     *
//     * @param joinPoint
//     */
//    @AfterReturning(value = "checkParam()")
//    fun doAfterReturning(joinPoint: ProceedingJoinPoint) {
//    }

    @Throws(Exception::class)
    fun checkArgs(join: ProceedingJoinPoint): Any {
        val args = join.args
        println("参数检查开始")
        args.forEach { any: Any? ->
            if (any != null) {
                BeanValidator.validate(any)
            }
        }
        println("参数检查结束")
        return join.proceed()
    }

}