/**
 * Copyright (C) 2016 The yuhaiyang Android Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.brightyu.androidcommon.modules.account.password.forgot;


import com.brightyu.androidcommon.modules.base.BasePresenter;
import com.brightyu.androidcommon.modules.base.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface ForgotPasswordContract {

    interface View extends BaseView<Presenter> {

        void showRegistering();

        void showRegisterFail(String message);

        void showRegisterSuccess();

        void showSendVerifySuccess();

        void showSendVerifyFail(String message);

    }

    interface Presenter extends BasePresenter {
        /**
         * 注册动作
         */
        void register(String name, String verifyCode, String password, String ensurePassword);

        /**
         * 发送验证码
         */
        void sendVerifiyCode();
    }
}
