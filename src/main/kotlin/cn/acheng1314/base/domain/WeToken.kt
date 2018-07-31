package cn.acheng1314.base.domain

import java.io.Serializable

/**
 * 微信access_token
 */
class WeToken : Serializable {
    var access_token: String? = null
    var expires_in: Int? = null
    var errcode: Int? = null
    var errmsg: String? = null

    override fun toString(): String {
        return "WeToken(access_token='$access_token', expires_in=$expires_in, errcode=$errcode, errmsg='$errmsg')"
    }


}