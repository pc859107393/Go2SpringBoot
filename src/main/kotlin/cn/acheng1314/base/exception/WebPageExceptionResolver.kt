package cn.acheng1314.base.exception

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class WebPageExceptionResolver : HandlerExceptionResolver {
    private val log = LoggerFactory.getLogger(WebPageExceptionResolver::class.java)
    override fun resolveException(request: HttpServletRequest, response: HttpServletResponse, handler: Any?, ex: Exception)
            : ModelAndView? = ModelAndView("error/error")
            .run {
                this.modelMap.addAttribute("msg", ex.message)
                this.modelMap.addAttribute("code", response.status)
                return@run this
            }.run { return this }
}