/**
 * Copyright (C) 2016 The yuhaiyang Android Source Project
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

package com.brightyu.androidcommon.modules.account.login;


import android.content.Context;

import com.ishow.common.mvp.base.BasePresenter;
import com.ishow.common.mvp.base.BaseView;
import com.ishow.common.mvp.base.IViewStatus;

/**
 * This specifies the contract between the view and the presenter.
 */
interface LoginContract {

    interface View extends BaseView, IViewStatus {


        /**
         * password 暂时占位，预防有记住密码功能
         */
        void updateUI(boolean rememberPassword, String account, String password);
    }

    interface Presenter extends BasePresenter {

        void login(Context context, String name, String password);
    }
}
