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


import tk.beason.common.mvp.base.IPresenter
import tk.beason.common.mvp.base.IView
import tk.beason.common.mvp.base.IViewStatus

/**
 * This specifies the contract between the view and the presenter.
 */
internal interface LoginContract {

    interface View : IView, IViewStatus {

        /**
         * 更新UI
         */
        fun updateUI(account: String)
    }

    interface Presenter : IPresenter {
        /**
         * init
         */
        fun init()
        /**
         * 登录
         */
        fun login(account: String, password: String)
    }
}
