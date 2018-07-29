package cn.acheng1314.base.domain

import java.io.Serializable

/**
 * 微信access_token
 */
class WeToken : Serializable {
    var access_token: String = ""
    var expires_in = 0
    var errcode = 0
    var errmsg = ""
}