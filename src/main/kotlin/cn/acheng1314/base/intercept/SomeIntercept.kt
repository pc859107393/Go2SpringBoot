package cn.acheng1314.base.intercept

import cn.acheng1314.base.utils.IPUtil
import cn.acheng1314.base.utils.SqlInjectCheckUtil
import org.slf4j.LoggerFactory
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.io.PrintWriter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * 常规的一些拦截器，如：sql注入、跨域支持
 */
@Component
class SomeIntercept : HandlerInterceptor {

    private val log = LoggerFactory.getLogger(SomeIntercept::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        //===================跨域处理==================如果参数不能接收，将Access-Control-Allow-Headers的值对应客户端请求数据对应修改
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Headers", "access-control-allow-headers,content-type,withcredentials")
        response.setHeader("X-Frame-Options", "SAMEORIGIN")
        response.setHeader("Access-Control-Allow-Credentials", "true")

        //==================sql注入处理===================
        //获得所有请求参数名
//        val params = request.parameterNames
//        val sql = StringBuilder()
//        while (params.hasMoreElements()) {
//            //得到参数名
//            val name = params.nextElement().toString()
//            //得到参数对应值
//            val value = request.getParameterValues(name)
//            for (s in value) {
//                sql.append(s)
//            }
//        }
//
//        //有sql关键字，跳转到error.html
//        if (SqlInjectCheckUtil.isSql(sql.toString())) {
//            log.info(String.format("检测到用户 %s 进行sql注入，ip = %s", request.session.id, IPUtil.getIpAddr(request)))
//            response.characterEncoding = "UTF-8"
//            response.contentType = "application/json; charset=utf-8"
//            val result = JSONObject()
//            result.put("code", "403")
//            result.put("description", "Were you do any sql inject? if you are success, please tell us, we will thanks for your help. No Can No BB!")
//            val out: PrintWriter = response.writer
//            out.append(result.toString())
//            return false
//        }

        return super.preHandle(request, response, handler)
    }


}