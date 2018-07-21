package cn.acheng1314.base.utils

import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * 字符串操作工具包<br></br>
 *
 *
 * **创建时间** 2014-8-14
 *
 * @author kymjs (https://github.com/kymjs)
 * @version 1.1
 */
object StringUtil {
    private val emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
    private val phone = Pattern
            .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$")

    /**
     * 返回当前系统时间
     */
    val dataTime: String
        get() {
            val df = SimpleDateFormat("yyyy-MM-dd")
            return df.format(Date())
        }

    private val dateFormater = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:shape_orage_aval")
        }
    }

    private val dateFormater2 = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("yyyy-MM-dd")
        }
    }

    /**
     * 判断用户的设备时区是否为东八区（中国） 2014年7月31日
     *
     * @return
     */
    val isInEasternEightZones: Boolean
        get() {
            var defaultVaule = true
            defaultVaule = TimeZone.getDefault() === TimeZone.getTimeZone("GMT+08")
            return defaultVaule
        }

    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    fun isEmpty(input: CharSequence?): Boolean {
        if (input == null || "" == input)
            return true
        (0 until input.length).forEach { i ->
            val c = input[i]
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false
            }
        }
        return true
    }

    /**
     * 判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串
     * @return 若输入字符串为null或空字符串，返回true
     * <br/>
     * 在java中我们的可变参数为： String... tmp， 但是在Kotlin中使用 vararg代表可变参数
     */
    fun isEmpty(vararg strs: CharSequence?): Boolean {
        strs.forEach { str ->
            if (isEmpty(str))
                return@isEmpty true
        }
        return false
    }

    /**
     * 字符串转换unicode
     */
    fun string2Unicode(string: String): String {

        val unicode = StringBuffer()

        for (i in 0 until string.length) {

            // 取出每一个字符
            val c = string[i]

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c.toInt()))
        }

        return unicode.toString()
    }

    /**
     * unicode 转字符串
     */
    fun unicode2String(unicode: String): String {

        val string = StringBuffer()

        val hex = unicode.split("\\\\u".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (i in 1 until hex.size) {

            // 转换出每一个代码点
            val data = Integer.parseInt(hex[i], 16)

            // 追加成string
            string.append(data.toChar())
        }

        return string.toString()
    }


    /**
     * 判断是不是一个合法的电子邮件地址
     */
    fun isEmail(email: CharSequence): Boolean {
        return if (isEmpty(email)) false else emailer.matcher(email).matches()
    }

    /**
     * 判断是不是一个合法的手机号码
     */
    fun isPhone(phoneNum: CharSequence): Boolean {
        return if (isEmpty(phoneNum)) false else phone.matcher(phoneNum).matches()
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    fun toInt(str: String, defValue: Int): Int {
        try {
            return Integer.parseInt(str)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return defValue
    }

    /**
     * 对象转整
     *
     * @param obj
     * @return 转换异常返回 0
     */
    fun toInt(obj: Any?): Int {
        return if (obj == null) 0 else toInt(obj.toString(), 0)
    }

    /**
     * String转long
     *
     * @param obj
     * @return 转换异常返回 0
     */
    fun toLong(obj: String): Long {
        try {
            return java.lang.Long.parseLong(obj)
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }

        return 0
    }

    /**
     * String转double
     *
     * @param obj
     * @return 转换异常返回 0
     */
    fun toDouble(obj: String): Double {
        try {
            return java.lang.Double.parseDouble(obj)
        } catch (ignored: Exception) {
        }

        return 0.0
    }

    /**
     * 字符串转布尔
     *
     * @param b
     * @return 转换异常返回 false
     */
    fun toBool(b: String): Boolean {
        try {
            return java.lang.Boolean.parseBoolean(b)
        } catch (ignored: Exception) {
        }

        return false
    }

    /**
     * 判断一个字符串是不是数字
     */
    fun isNumber(str: CharSequence): Boolean {
        try {
            Integer.parseInt(str.toString())
        } catch (e: Exception) {
            return false
        }

        return true
    }

    /**
     * 16进制表示的字符串转换为字节数组。
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val d = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character
                    .digit(s[i + 1], 16)).toByte()
            i += 2
        }
        return d
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    fun friendlyTime(sdate: String): String {
        var time: Date? = null

        time = if (isInEasternEightZones) {
            toDate(sdate)
        } else {
            transformTime(toDate(sdate), TimeZone.getTimeZone("GMT+08"),
                    TimeZone.getDefault())
        }

        if (time == null) {
            return "Unknown"
        }
        var ftime = ""
        val cal = Calendar.getInstance()

        // 判断是否是同一天
        val curDate = dateFormater2.get().format(cal.time)
        val paramDate = dateFormater2.get().format(time)
        if (curDate == paramDate) {
            val hour = ((cal.timeInMillis - time.time) / 3600000).toInt()
            if (hour == 0)
                ftime = Math.max(
                        (cal.timeInMillis - time.time) / 60000, 1).toString() + "分钟前"
            else
                ftime = hour.toString() + "小时前"
            return ftime
        }

        val lt = time.time / 86400000
        val ct = cal.timeInMillis / 86400000
        val days = (ct - lt).toInt()
        if (days == 0) {
            val hour = ((cal.timeInMillis - time.time) / 3600000).toInt()
            if (hour == 0)
                ftime = Math.max(
                        (cal.timeInMillis - time.time) / 60000, 1).toString() + "分钟前"
            else
                ftime = hour.toString() + "小时前"
        } else if (days == 1) {
            ftime = "昨天"
        } else if (days == 2) {
            ftime = "前天 "
        } else if (days in 3..30) {
            ftime = days.toString() + "天前"
        } else if (days >= 31 && days <= 2 * 31) {
            ftime = "一个月前"
        } else if (days > 2 * 31 && days <= 3 * 31) {
            ftime = "2个月前"
        } else if (days > 3 * 31 && days <= 4 * 31) {
            ftime = "3个月前"
        } else {
            ftime = dateFormater2.get().format(time)
        }
        return ftime
    }

    fun toDate(sdate: String): Date? = toDate(sdate, dateFormater.get())

    fun toDate(sdate: String, dateFormat: SimpleDateFormat): Date? = try {
        dateFormat.parse(sdate)
    } catch (e: Exception) {
        null
    }

    /**
     * 根据不同时区，转换时间 2014年7月31日
     */
    fun transformTime(date: Date?, oldZone: TimeZone,
                      newZone: TimeZone): Date? {
        var finalDate: Date? = null
        if (date != null) {
            val timeOffset = oldZone.getOffset(date.time) - newZone.getOffset(date.time)
            finalDate = Date(date.time - timeOffset)
        }
        return finalDate
    }

    /**
     * 将url编码中的空格符号删掉，并且将剩下内容再次转码一次
     *
     * @param mStr
     * @return
     */
    fun replaceStr(mStr: String): String {
        var mStr = mStr
        val resultStr: String

        if (mStr.contains("%0A")) {
            mStr = mStr.replace("%0A", "")
        }
        resultStr = if (mStr.contains("%")) {
            mStr.replace("%", "%25")
        } else {
            mStr
        }

        return resultStr
    }

    /**
     * 将字符串转码为URL编码
     *
     * @param srcStr 源字符串
     * @return URLEncode
     */
    fun str2UrlCode(srcStr: String): String {
        var srcStr = srcStr
        srcStr = if (srcStr.contains(" ")) srcStr.replace(" ", "") else srcStr
        if (srcStr.isEmpty()) {
            srcStr = " "
        }
        return try {
            URLEncoder.encode(srcStr, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            ""
        }

    }

    /**
     * 判断多个字符串数据是不是为空
     *
     * @param key 多个字符串是否为空
     * @return 为空就返回true
     */
    fun areNotEmpty(vararg key: String): Boolean {
        for (tmp in key) {
            if (isEmpty(tmp)) {
                return true
            }
        }
        return false
    }

    /**
     * 获取32位的UUid
     */
    val uuid32: String
        get() =
            UUID.randomUUID().toString().trim { it <= ' ' }.replace("-".toRegex(), "")

}