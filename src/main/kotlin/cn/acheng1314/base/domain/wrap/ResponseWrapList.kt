package cn.acheng1314.base.domain.wrap

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.springframework.http.HttpStatus
import java.io.Serializable

class ResponseWrapList<T> : Serializable {
    var code: Int = 200
    var status: String = "OK"
    var pageNum: Int = 1
    var pageSize: Int = 15
    var totalPage: Int = 0
    var data: MutableCollection<T> = arrayListOf()

    fun notFound(): ResponseWrapList<T> {
        code = HttpStatus.NOT_FOUND.value()
        status = HttpStatus.NOT_FOUND.reasonPhrase
        return this
    }

    fun warp(page: Page<T>?): ResponseWrapList<T> {
        if (page == null) return@warp notFound()
        page.run {
            if (page.records.isEmpty()) return@warp empty()
            pageNum = page.current.toInt()
            pageSize = page.size.toInt()
            totalPage = page.pages.toInt()
            data.addAll(page.records)
            code = HttpStatus.OK.value()
            status = HttpStatus.OK.reasonPhrase
        }
        return this
    }

    fun empty(): ResponseWrapList<T> {
        code = HttpStatus.NO_CONTENT.value()
        status = HttpStatus.NO_CONTENT.reasonPhrase
        return this
    }


}