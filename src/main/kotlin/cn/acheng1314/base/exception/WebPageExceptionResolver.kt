package cn.acheng1314.base.exception

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import redis.clients.jedis.exceptions.JedisConnectionException
import redis.clients.jedis.exceptions.JedisException
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class WebPageExceptionResolver : HandlerExceptionResolver {

    private val log = LoggerFactory.getLogger(WebPageExceptionResolver::class.java)

    /**
     * 全局网页异常处理，kotlin可以直接使用 = 给函数赋值返回值，前提是一行简单语句
     * @see run 在kotlin中有作用域函数run、let、with、apply、also请看 https://user-gold-cdn.xitu.io/2018/4/1/1627ee8160e9fee3?imageslim
     * @return 返回错误页面的ModelAndView
     */
    override fun resolveException(request: HttpServletRequest, response: HttpServletResponse, handler: Any?, ex: Exception)
            : ModelAndView? = ModelAndView("error/error")
            .run {
                //在Java中一般用 ex instanceof xxxException，kotlin中使用 ex is xxxException
                if (ex is JedisConnectionException
                        || ex is JedisException)
                    this.modelMap.addAttribute("msg", "Redis链接异常，请检查！")
                else
                    this.modelMap.addAttribute("msg", ex.message)
                this.modelMap.addAttribute("path", request.requestURL)
                return@run this
            }.run { return this }
}