package com.ishow.noah.constant

import com.ishow.common.utils.StringUtils

/**
 * Created by yuhaiyang on 2018/3/22.
 * 连接地址
 */

object Url {
    private val baseUrl: String
        get() = "https://api.yuhaiyang.net/common"

    /**
     * 登录
     */
    fun login(): String {
        return StringUtils.plusString(baseUrl, "/account/login")
    }

    /**
     * 登录
     */
    fun loginByToken(): String {
        return StringUtils.plusString(baseUrl, "/account/loginByToken")
    }

    /**
     * 注册
     */
    fun register(): String {
        return StringUtils.plusString(baseUrl, "/account/register")
    }

    /**
     * 忘记密码
     */
    fun forgotPassword(): String {
        return StringUtils.plusString(baseUrl, "/account/forgotPassword")
    }

    /**
     * 上传头像
     */
    fun uploadAvatar(): String {
        return StringUtils.plusString(baseUrl, "/account/uploadAvatar")
    }
}