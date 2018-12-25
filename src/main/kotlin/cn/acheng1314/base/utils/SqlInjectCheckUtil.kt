package cn.acheng1314.base.utils

import java.util.regex.Pattern

object SqlInjectCheckUtil {

    /**
     * sql关键字检查
     *
     * @param str
     * @return
     */
    fun isSql(str: String): Boolean {
        var str = str
        str = str.toLowerCase()//统一转为小写

        val regEx = "\\b(select|update|union|and|or|delete|insert|trancate|char|substr|ascii|declare|exec|count|master|into|drop|execute|sitename|net|user|xp_cmdshell|create|table|from|grant|use|group_concat|column_name|information_schema.columns|table_schema|where|when|thenlike|as|case|chr|mid|truncate|#|v4ax8kvm|aggregate|analyse)\\b"

        val pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE)
        val matcher = pat.matcher(str)
        return matcher.find()
    }


}