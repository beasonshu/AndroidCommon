/**
 * Copyright (C) 2016 The yuhaiyang Android Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tk.beason.noah.modules.base


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.*
import kotlinx.android.synthetic.main.activity_only_web.*

import tk.beason.common.utils.WebViewUtils
import tk.beason.common.utils.log.LogManager
import tk.beason.noah.R

/**
 * Created by Bright.Yu on 2016/8/9.
 */
class OnlyWebActivity : tk.beason.noah.modules.base.AppBaseActivity() {

    private var mTitleString: String? = null
    private var mUrl: String? = null
    private var mOnErrorUrl: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_only_web)
    }


    override fun initNecessaryData() {
        super.initNecessaryData()
        val intent = intent
        mTitleString = intent.getStringExtra(KEY_TITLE)
        mUrl = intent.getStringExtra(KEY_CONTENT)
        if (tk.beason.noah.BuildConfig.DEBUG) LogManager.i(TAG, "initNecessaryData: mUrl = " + mUrl!!)
    }

    override fun initViews() {
        super.initViews()
        topBar.setText(mTitleString)

        WebViewUtils.init(this, web)
        //载入js
        web.loadUrl(mUrl)
        //载入js
        web.webViewClient = WebClient()
        // 设置下载监听
        web.setDownloadListener { url, _, _, _, _ ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        if (web != null && web.canGoBack() && !mOnErrorUrl) {
            web.goBack()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val TAG = "OnlyWebActivity"
        /**
         * 要显示的标题
         */
        const val KEY_TITLE = "key_title"
        /**
         * 要显示的地址
         */
        const val KEY_CONTENT = "key_content"
    }


    private inner class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            if (request == null || !request.isForMainFrame) {
                return
            }
            mOnErrorUrl = true
        }

        override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
            super.onReceivedHttpError(view, request, errorResponse)

            if (request == null || !request.isForMainFrame) {
                return
            }

            mOnErrorUrl = true
        }
    }
}
