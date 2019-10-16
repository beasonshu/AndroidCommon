/**
 * Copyright (C) 2016 The yuhaiyang Android Source Project
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tk.beason.noah.modules.account.password.forgot


import android.content.Context

import tk.beason.common.mvp.base.IPresenter
import tk.beason.common.mvp.base.IView
import tk.beason.common.mvp.base.IViewStatus


/**
 * Created by yuhaiyang on 2018/8/8.
 * 忘记密码的Contract
 */
internal interface ForgotPasswordContract {

    interface View : IView {

        /**
         * 发生验证码成功
         */
        fun showSendVerifySuccess()

        /**
         * 发送验证码失败
         * @param message 错误信息
         */
        fun showSendVerifyFail(message: String)
    }

    interface Presenter : IPresenter {

        /**
         * 重置密码
         */
        fun resetPassword(context: Context, name: String, verifyCode: String, password: String, ensurePassword: String)

        /**
         * 发送验证码
         */
        fun sendVerifyCode(context: Context, phoneNumber: String)
    }
}
