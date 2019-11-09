/*
 * Copyright (C) 2017. The beasontk Android Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tk.beason.noah.modules.egg.detail

import android.os.Bundle
import androidx.core.content.ContextCompat
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView

import tk.beason.common.utils.AppUtils
import tk.beason.common.utils.DateUtils
import tk.beason.common.utils.DeviceUtils
import tk.beason.common.utils.StringUtils
import tk.beason.common.widget.textview.TextViewPro
import tk.beason.noah.R

/**
 * Created by beasontk on 2017/6/1.
 * 彩蛋详情
 */

class EggAppInfoActivity : tk.beason.noah.modules.base.AppBaseActivity() {
    private var mRootView: LinearLayout? = null
    private var mItemMinHeight: Int = 0
    private var mCateTextSize: Int = 0
    private var mCateTextColor: Int = 0
    private var mTipMinWidth: Int = 0


    private val lineParams: LinearLayout.LayoutParams
        get() {
            val screenWidth = DeviceUtils.getScreenSize()[0]
            val lp = LinearLayout.LayoutParams(0, 1)
            lp.weight = 1f
            lp.leftMargin = screenWidth / 10
            lp.rightMargin = screenWidth / 10
            return lp
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_egg_app_info)
        init()
    }

    override fun initNecessaryData() {
        super.initNecessaryData()
        mItemMinHeight = resources.getDimensionPixelSize(R.dimen.default_item_height)
        mCateTextSize = resources.getDimensionPixelSize(R.dimen.I_title)
        mCateTextColor = ContextCompat.getColor(context, R.color.text_grey_light_normal)
        mTipMinWidth = resources.getDimensionPixelSize(R.dimen.dp_100)
    }

    override fun initViews() {
        super.initViews()
        mRootView = findViewById(R.id.root)
    }


    private fun init() {
        val screen = DeviceUtils.getScreenSize()
        addCate("Android")
        addItem("分辨率：", StringUtils.plusString(screen[1], "*", screen[0]))
        addItem("手机型号：", DeviceUtils.model())
        addItem("手机版本：", DeviceUtils.version())
        addItem("最小宽度：", resources.configuration.smallestScreenWidthDp)

        addCate("App")
        addItem("包名：", packageName)
        addItem("版本号：", AppUtils.getVersionName(this))
        addItem("版本编号：", AppUtils.getVersionCode(this))
        addItem("版本类型：", tk.beason.noah.BuildConfig.VERSION_DESCRIPTION)
        addItem("发布时间：", DateUtils.format(tk.beason.noah.BuildConfig.RELEASE_TIME, DateUtils.FORMAT_YMDHMS))
    }


    private fun addCate(name: String) {
        val container = LinearLayout(this)
        container.orientation = LinearLayout.HORIZONTAL
        container.gravity = Gravity.CENTER
        container.setBackgroundResource(android.R.color.white)
        container.minimumHeight = (mItemMinHeight * 0.6f).toInt()

        var line = View(this)
        line.setBackgroundResource(R.color.line)
        line.layoutParams = lineParams
        container.addView(line)

        val textView = AppCompatTextView(this)
        textView.setTextColor(mCateTextColor)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCateTextSize.toFloat())
        textView.text = name
        container.addView(textView)

        line = View(this)
        line.setBackgroundResource(R.color.line)
        line.layoutParams = lineParams
        container.addView(line)
        mRootView!!.addView(container)
    }


    private fun addItem(name: String, value: Int) {
        addItem(name, value.toString())
    }

    private fun addItem(name: String, value: String) {
        val textView = TextViewPro(this)
        textView.minimumHeight = mItemMinHeight
        textView.setBackgroundResource(android.R.color.white)
        textView.setLeftText(name)
        textView.setLeftTextMinWidth(mTipMinWidth)
        textView.setLeftTextGravity(Gravity.END or Gravity.CENTER_VERTICAL)
        textView.setText(value)
        textView.setTextColor(mCateTextColor)
        textView.setLeftImageVisibility(View.GONE)
        textView.setRightImageVisibility(View.GONE)
        mRootView!!.addView(textView)
    }
}
