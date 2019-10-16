package tk.beason.noah.modules.sample.pulltorefresh

import android.content.Context
import tk.beason.noah.modules.sample.entries.page.PageJob
import tk.beason.noah.utils.http.AppHttpCallBack
import tk.beason.common.utils.http.rest.Http
import tk.beason.common.utils.http.rest.HttpError

internal class SamplePullToRefreshPresenter(private val mView: SamplePullToRefreshContract.View) : tk.beason.noah.modules.sample.pulltorefresh.SamplePullToRefreshContract.Presenter {


    override fun getList(context: Context, pageNumber: Int, loadingView: Boolean) {
        if (loadingView) mView.showLoading(null, false)
        val url = "http://xiaomidagong.com/Home/SelectFactoryInfoType?title=&page=$pageNumber&pagesize=10"
        Http.post()
                .url(url)
                .execute(object : AppHttpCallBack<PageJob>(context) {
                    override fun onFailed(error: HttpError) {
                        if (loadingView) mView.showError(null, false, error.code)
                    }

                    override fun onSuccess(result: tk.beason.noah.modules.sample.entries.page.PageJob?) {
                        if (result == null || result.getResults().isEmpty()) {
                            if (loadingView) mView.showEmpty(null)
                        } else {
                            if (loadingView) mView.dismissLoading(false)
                            mView.updateView(result.getResults(), result.getTotalRows())
                        }
                    }
                })
    }
}