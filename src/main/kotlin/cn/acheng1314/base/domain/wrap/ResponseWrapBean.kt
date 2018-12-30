package cn.acheng1314.base.domain.wrap

import org.springframework.http.HttpStatus
import java.io.Serializable

class ResponseWrapBean<T> : Serializable {
    var code: Int = 200
    var status: String = "OK"
    var data: Any? = null

    fun warp(bean: T?): ResponseWrapBean<T> {
        if (bean == null) return notFound()
        code = HttpStatus.OK.value()
        status = HttpStatus.OK.reasonPhrase
        data = bean
        return this
    }

    fun notFound(): ResponseWrapBean<T> {
        code = HttpStatus.NOT_FOUND.value()
        status = HttpStatus.NOT_FOUND.reasonPhrase
        return this
    }

}