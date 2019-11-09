package tk.beason.noah.modules.main.mine

import android.content.Context

import tk.beason.common.mvp.base.IPresenter
import tk.beason.common.mvp.base.IView
import tk.beason.common.mvp.base.IViewStatus
import tk.beason.noah.entries.UserContainer

/**
 * Created by beasontk on 2018/3/28.
 * Contract
 */

internal interface MineContract {
    interface View : IView, IViewStatus {
        /**
         * 更新信息
         */
        fun update(userContainer: tk.beason.noah.entries.UserContainer?)

    }

    interface Presenter : IPresenter {

        fun onResume(context: Context?)
    }
}
