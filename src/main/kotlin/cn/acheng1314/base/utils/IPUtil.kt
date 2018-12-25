package cn.acheng1314.base.utils

import org.apache.commons.lang3.StringUtils
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest

/**
 * ip的工具类
 */
object IPUtil {


    /**
     * isInnerIP:判断内网IP
     *
     * @param ipAddress
     * @return
     * @创建时间 2016-3-26 下午5:15:32
     * @作者 ciano
     */
    fun isInnerIP(ipAddress: String): Boolean {
        var isInnerIp = false
        try {
            val ipNum = getIpNum(ipAddress)
            /**
             * 私有IP：A类 10.0.0.0-10.255.255.255 B类 172.16.0.0-172.31.255.255 C类
             * 192.168.0.0-192.168.255.255 当然，还有127这个网段是环回地址
             */
            val aBegin = getIpNum("10.0.0.0")
            val aEnd = getIpNum("10.255.255.255")
            val bBegin = getIpNum("172.16.0.0")
            val bEnd = getIpNum("172.31.255.255")
            val cBegin = getIpNum("192.168.0.0")
            val cEnd = getIpNum("192.168.255.255")
            isInnerIp = (isInner(ipNum, aBegin, aEnd)
                    || isInner(ipNum, bBegin, bEnd)
                    || isInner(ipNum, cBegin, cEnd)
                    || ipAddress == "127.0.0.1")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return isInnerIp
    }

    fun getIpNum(ipAddress: String): Long {
        val ip = ipAddress.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val a = Integer.parseInt(ip[0]).toLong()
        val b = Integer.parseInt(ip[1]).toLong()
        val c = Integer.parseInt(ip[2]).toLong()
        val d = Integer.parseInt(ip[3]).toLong()

        return a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d
    }

    fun isInner(userIp: Long, begin: Long, end: Long): Boolean {
        return userIp in begin..end
    }

    /**
     * getIpAddr:获取当前网络ip
     *
     * @param request
     * @return
     * @创建时间 2016-3-26 下午5:15:23
     * @作者 ciano
     */
    fun getIpAddr(request: HttpServletRequest): String? {
        try {
            var ipAddress: String? = request.getHeader("x-forwarded-for")
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.getHeader("Proxy-Client-IP")
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP")
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.remoteAddr
                if (ipAddress == "127.0.0.1" || ipAddress == "0:0:0:0:0:0:0:1") {
                    // 根据网卡取本机配置的IP
                    var inet: InetAddress? = null
                    try {
                        inet = InetAddress.getLocalHost()
                    } catch (e: UnknownHostException) {
                        e.printStackTrace()
                    }

                    ipAddress = inet!!.hostAddress
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length > 15) { // "***.***.***.***".length()=
                // 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","))
                }
            }
            return ipAddress
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    fun isInRange(ip: String, beginIp: String, endIp: String): Boolean {
        val ipValue = getIp2long(ip)
        val beginIpValue = getIp2long(beginIp)
        val endIpValue = getIp2long(endIp)
        return ipValue in beginIpValue..endIpValue
    }

    fun getIp2long(ip: String): Long {
        var ip = ip
        if (StringUtils.isBlank(ip)) {
            return 0
        }

        ip = ip.trim { it <= ' ' }

        val ips = ip.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var result = 0L
        for (i in 0..3) {
            result = result shl 8 or Integer.parseInt(ips[i]).toLong()
        }

        return result
    }

    fun isValidIp(str: String): Boolean {
        val pattern = Pattern.compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$")
        return pattern.matcher(str).matches()
    }


}