package tk.beason.noah.modules.account.modify

import android.content.Context

import tk.beason.common.mvp.base.IPresenter
import tk.beason.common.mvp.base.IView
import tk.beason.common.mvp.base.IViewStatus
import tk.beason.noah.entries.User

/**
 * Created by beasontk on 2018/8/8.
 * 修改用户信息-Contract
 */
internal interface ModifyUserInfoContract {

    interface View : IView {

        /**
         * 更新成功
         */
        fun updateAvatar(avatar: String)
    }

    interface Presenter : IPresenter {
        /**
         * 修改头像
         */
        fun modifyAvatar(context: Context, avatar: String)

        /**
         * 更新用户信息
         */
        fun modifyUserInfo(context: Context, user: tk.beason.noah.entries.User)
    }
}