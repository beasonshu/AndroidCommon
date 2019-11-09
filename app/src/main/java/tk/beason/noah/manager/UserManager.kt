/*
 * Copyright (C) 2016 The beasontk Android Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tk.beason.noah.manager


import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.alibaba.fastjson.JSON
import tk.beason.common.utils.RegexValidateUtils
import tk.beason.common.utils.StorageUtils
import tk.beason.common.utils.StringUtils
import tk.beason.noah.R
import tk.beason.noah.entries.UserContainer

/**
 * Created by beasontk on 2018/8/8.
 * 用户信息管理
 */
class UserManager private constructor() {

    private var mUserContainer: tk.beason.noah.entries.UserContainer? = null


    /**
     * 设置UserContainer
     */
    fun setUserContainer(context: Context, container: tk.beason.noah.entries.UserContainer?) {
        mUserContainer = container
        StorageUtils.with(context)
                .param(tk.beason.noah.entries.UserContainer.Key.CACHE, JSON.toJSONString(container))
                .save()
    }

    /**
     * 获取用户信息
     */
    fun getUserContainer(context: Context?): tk.beason.noah.entries.UserContainer? {
        if(context == null){
            return mUserContainer
        }

        if (mUserContainer == null) {
            val cache = StorageUtils.with(context)
                    .key(tk.beason.noah.entries.UserContainer.Key.CACHE)
                    .get<String>(null)

            if (TextUtils.isEmpty(cache)) {
                Log.i(tk.beason.noah.manager.UserManager.Companion.TAG, "getUser: no user")
                return null
            }
            mUserContainer = JSON.parseObject(cache, tk.beason.noah.entries.UserContainer::class.java)
        }
        return mUserContainer
    }


    /**
     * 获取头像
     */
    fun getAvatar(context: Context): String? {
        if (mUserContainer == null) {
            val cache = StorageUtils.with(context)
                    .key(tk.beason.noah.entries.UserContainer.Key.CACHE)
                    .get<String>(null)

            if (TextUtils.isEmpty(cache)) {
                Log.i(tk.beason.noah.manager.UserManager.Companion.TAG, "getUser: no user")
                return null
            }
            mUserContainer = JSON.parseObject(cache, tk.beason.noah.entries.UserContainer::class.java)
        }
        return if (mUserContainer == null) {
            StringUtils.EMPTY
        } else mUserContainer!!.user.avatar

    }

    /**
     * 获取Accessoken
     */
    fun getAccessToken(context: Context): String {
        val userContainer = getUserContainer(context) ?: return StringUtils.EMPTY
        val token = userContainer.token ?: return StringUtils.EMPTY
        return token.accessToken
    }

    companion object {
        private val TAG = "UserManager"

        @Volatile
        private var sInstance: tk.beason.noah.manager.UserManager? = null


        val instance: tk.beason.noah.manager.UserManager
            get() {
                if (tk.beason.noah.manager.UserManager.Companion.sInstance == null) {
                    synchronized(tk.beason.noah.manager.UserManager::class.java) {
                        if (tk.beason.noah.manager.UserManager.Companion.sInstance == null) {
                            tk.beason.noah.manager.UserManager.Companion.sInstance =
                                tk.beason.noah.manager.UserManager()
                        }
                    }
                }
                return tk.beason.noah.manager.UserManager.Companion.sInstance!!
            }

        /**
         * 检测账户是否有效
         */
        fun checkAccount(context: Context, account: String): String {
            if (TextUtils.isEmpty(account)) {
                return context.getString(R.string.login_please_input_account)
            }
            return if (!RegexValidateUtils.checkMobileNumber(account)) {
                context.getString(R.string.login_please_input_correct_account)
            } else StringUtils.EMPTY
        }

        /**
         * 检测密码是否有效
         */
        fun checkPassword(context: Context, password: String): String {
            if (TextUtils.isEmpty(password)) {
                return context.getString(R.string.login_please_input_password)
            }

            val length = password.length
            val min = context.resources.getInteger(R.integer.min_password)
            val max = context.resources.getInteger(R.integer.max_password)
            return if (length < min || length > max) {
                context.getString(R.string.login_please_input_correct_password, min.toString(), max.toString())
            } else StringUtils.EMPTY

        }

        /**
         * 检测再次输入的密码是否有效
         */
        fun checkEnsurePassword(context: Context, password: String, ensurePassword: String): String {
            if (TextUtils.isEmpty(ensurePassword)) {
                return context.getString(R.string.please_input_ensure_password)
            }

            return if (!TextUtils.equals(password, ensurePassword)) {
                context.getString(R.string.please_input_right_ensure_password)
            } else StringUtils.EMPTY
        }
    }
}
