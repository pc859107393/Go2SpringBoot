package cn.acheng1314.base.exception

import cn.acheng1314.base.domain.wrap.ResponseWrapBean
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
@ResponseBody
class GlobalApiExceptionHandler {

    @ExceptionHandler(value = [(ApiException::class)])
    fun resolveApi(request: HttpServletRequest, exception: ApiException): ResponseWrapBean<String> {
        val result = ResponseWrapBean<String>()
        result.code = HttpStatus.INTERNAL_SERVER_ERROR.value()
        result.status = HttpStatus.INTERNAL_SERVER_ERROR.name
        result.data = exception.message ?: "服务器异常！"
        return result
    }

}