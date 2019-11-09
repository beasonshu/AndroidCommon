/**
 * Copyright (C) 2016 The beasontk Android Source Project
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

import android.os.Bundle
import android.view.View
import tk.beason.noah.R
import tk.beason.noah.modules.base.AppBaseActivity
import kotlinx.android.synthetic.main.activity_password.*

/**
 * Created by beasontk on 2018/8/8.
 * 修改密码和重置密码一系类的东西
 * 和注册分开预防后期业务更改
 */
class ForgotPasswordActivity : tk.beason.noah.modules.base.AppBaseActivity(), View.OnClickListener,
    tk.beason.noah.modules.account.password.forgot.ForgotPasswordContract.View {

    private lateinit var mPresenter: tk.beason.noah.modules.account.password.forgot.ForgotPasswordContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        mPresenter = tk.beason.noah.modules.account.password.forgot.ForgotPasswordPresenter(this)
    }

    override fun initViews() {
        super.initViews()
        sendVerifyCode.setOnClickListener(this)
        submit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.sendVerifyCode -> {
                sendVerifyCode.showLoading()
                mPresenter.sendVerifyCode(this, phone.inputText)
            }
            R.id.submit -> {
                val phone = phone.inputText
                val verifyCode = verifyCode.inputText
                val password = password.inputText
                val passwordEnsure = ensurePassword.inputText
                mPresenter.resetPassword(context, phone, verifyCode, password, passwordEnsure)
            }
        }
    }

    override fun showSendVerifySuccess() {
        sendVerifyCode.startTiming()
    }

    override fun showSendVerifyFail(message: String) {
        sendVerifyCode.reset()
        dialog(message)
    }

}
