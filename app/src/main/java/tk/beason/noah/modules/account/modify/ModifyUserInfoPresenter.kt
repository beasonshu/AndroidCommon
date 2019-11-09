package tk.beason.noah.modules.account.modify

import android.content.Context

import tk.beason.common.utils.http.rest.Http
import tk.beason.common.utils.http.rest.HttpError
import tk.beason.noah.constant.Url
import tk.beason.noah.entries.User
import tk.beason.noah.manager.UserManager
import tk.beason.noah.utils.http.AppHttpCallBack

import java.io.File

/**
 * Created by beasontk on 2018/8/8.
 * 修改用户信息的Presenter
 */
internal class ModifyUserInfoPresenter(private val mView: tk.beason.noah.modules.account.modify.ModifyUserInfoContract.View) :
    tk.beason.noah.modules.account.modify.ModifyUserInfoContract.Presenter {

    override fun modifyAvatar(context: Context, avatar: String) {
        val userManager = tk.beason.noah.manager.UserManager.instance
        mView.showLoading(null, true)
        Http.post()
                .url(tk.beason.noah.constant.Url.uploadAvatar())
                .addHeader("token", userManager.getAccessToken(context))
                .addParams("file", File(avatar))
                .execute(object : AppHttpCallBack<String>(context) {
                    override fun onFailed(error: HttpError) {
                        mView.dismissLoading(true)
                        mView.showError(error.message, true, 0)
                    }

                    override fun onSuccess(result: String) {
                        mView.dismissLoading(true)
                        mView.updateAvatar(avatar)
                    }
                })
    }

    override fun modifyUserInfo(context: Context, user: tk.beason.noah.entries.User) {

    }
}