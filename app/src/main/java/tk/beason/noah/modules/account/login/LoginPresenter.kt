/*
 * Copyright (C) 2016 The beasontk Android Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tk.beason.noah.modules.account.login

import android.text.TextUtils
import com.alibaba.fastjson.JSONObject
import tk.beason.common.utils.DeviceUtils
import tk.beason.common.utils.StorageUtils
import tk.beason.common.utils.StringUtils
import tk.beason.common.utils.http.rest.Http
import tk.beason.common.utils.http.rest.HttpError
import tk.beason.noah.entries.UserContainer
import tk.beason.noah.utils.http.AppHttpCallBack

/**
 * Created by beasontk on 2018/8/8.
 * 登录的Presenter
 */
internal class LoginPresenter(private val mView: LoginContract.View) :
    LoginContract.Presenter {

    override fun init() {
        val account = StorageUtils.with(mView.context)
                .key(UserContainer.Key.ACCOUNT)
                .get(StringUtils.EMPTY)

        mView.updateUI(account)
        clear()
    }

    override fun login(account: String, password: String) {
        var errorMessage = tk.beason.noah.manager.UserManager.checkAccount(mView.context, account)
        if (!TextUtils.isEmpty(errorMessage)) {
            mView.showError(errorMessage, true, 0)
            return
        }

        errorMessage = tk.beason.noah.manager.UserManager.checkPassword(mView.context, password)
        if (!TextUtils.isEmpty(errorMessage)) {
            mView.showError(errorMessage, true, 0)
            return
        }

        saveUserInfo(account)

        mView.showLoading(null, true)

        val params = JSONObject()
        params["account"] = account
        params["password"] = password
        params["device"] = tk.beason.noah.constant.Configure.DEVICE
        params["deviceModel"] = DeviceUtils.model()
        params["deviceVersion"] = DeviceUtils.version()

        Http.post()
                .url(tk.beason.noah.constant.Url.login())
                .params(params.toJSONString())
                .execute(object : AppHttpCallBack<tk.beason.noah.entries.UserContainer>(mView.context) {
                    override fun onFailed(error: HttpError) {
                        mView.dismissLoading(true)
                        mView.showError(error.message, true, 0)
                    }

                    override fun onSuccess(result: tk.beason.noah.entries.UserContainer) {
                        val userManager = tk.beason.noah.manager.UserManager.instance
                        userManager.setUserContainer(mView.context, result)
                        mView.dismissLoading(true)
                        mView.showSuccess(StringUtils.EMPTY)
                    }
                })
    }


    /**
     * 保存用户信息
     */
    private fun saveUserInfo(account: String) {
        StorageUtils.with(mView.context)
                .param(tk.beason.noah.entries.UserContainer.Key.ACCOUNT, account)
                .save()
    }

    /**
     * 清除用户缓存
     */
    private fun clear() {
        StorageUtils.with(mView.context)
                .key(tk.beason.noah.entries.UserContainer.Key.CACHE)
                .remove()
    }
}
