package com.ishow.noah.modules.main.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ishow.common.extensions.inflate
import com.ishow.common.utils.router.AppRouter
import com.ishow.common.widget.tablayout.TabLayoutPro
import com.ishow.noah.R
import com.ishow.noah.modules.base.AppBaseFragment
import com.ishow.noah.modules.sample.main.SampleMainActivity
import kotlinx.android.synthetic.main.fragement_tab_1.*

/**
 * Created by yuhaiyang on 2017/4/21.
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topBar.setOnTopBarListener(this)
        tabLayout.addTab(tabLayout.newTab().setText("储蓄卡"))
        tabLayout.addTab(tabLayout.newTab().setText("信用卡"))

        tabLayout2.addTab(tabLayout2.newTab().setText("储蓄卡"))
        tabLayout2.addTab(tabLayout2.newTab().setText("信用卡"))
    }


    override fun onRightClick(v: View) {
        super.onRightClick(v)
        val intent = Intent("com.yuhaiyang.androidcommon.Test")
        startActivity(intent)
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