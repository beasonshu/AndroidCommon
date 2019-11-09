package tk.beason.noah.modules.main.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tk.beason.common.extensions.inflate
import tk.beason.common.utils.router.AppRouter
import tk.beason.common.widget.tablayout.TabLayoutPro
import tk.beason.noah.R
import tk.beason.noah.modules.base.AppBaseFragment
import tk.beason.noah.modules.sample.main.SampleMainActivity
import kotlinx.android.synthetic.main.fragement_tab_1.*

/**
 * Created by beasontk on 2017/4/21.
 * Home Fragment
 */

class HomeFragment : AppBaseFragment() {

    private var mRootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView != null) {
            return mRootView
        }

        mRootView = container?.inflate(R.layout.fragement_tab_1)
        return mRootView
    }




    override fun onRightClick(v: View) {
        super.onRightClick(v)
        AppRouter.with(context)
            .target(SampleMainActivity::class.java)
            .start()
    }

    companion object {

        fun newInstance(): HomeFragment {

            val args = Bundle()

            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
