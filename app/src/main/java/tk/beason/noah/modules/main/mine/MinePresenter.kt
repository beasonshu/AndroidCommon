package tk.beason.noah.modules.main.mine

import android.content.Context

import tk.beason.noah.entries.UserContainer
import tk.beason.noah.manager.UserManager

internal class MinePresenter(private val mView: MineContract.View) : MineContract.Presenter {

    override fun onResume(context: Context?) {
        val userManager = tk.beason.noah.manager.UserManager.instance
        val userContainer = userManager.getUserContainer(context)
        mView.update(userContainer)
    }
}