package cn.acheng1314.base.validate

import cn.acheng1314.base.exception.ApiException
import cn.acheng1314.base.utils.SqlInjectCheckUtil
import cn.acheng1314.base.utils.StringUtil
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * sql 注入检测
 */
@Aspect
@Component
class CheckSqlInjectValidAspect {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Pointcut(value = "@annotation(cn.acheng1314.base.validate.annotation.SqlInjectCheck) ")
    fun checkSqlInject() {
    }

    @Around(value = "checkSqlInject()")
    fun doBefore(joinPoint: ProceedingJoinPoint): Any = checkArgs(joinPoint)

    @Throws(Exception::class)
    fun checkArgs(join: ProceedingJoinPoint): Any {
        logger.info("sql注入检查开始！")
        val args = join.args
        args.forEach { any: Any? -> if (any != null && !StringUtil.isEmpty(any.toString())) validate(any) }
        return join.proceed()
    }


    fun <T> validate(`object`: T) {
        if (SqlInjectCheckUtil.isSql(`object`.toString())) {
            logger.info("发现sql注入！" + `object`.toString())
            throw ApiException("发现sql注入！参数为：" + `object`.toString())
        }
    }

}