/*
 * Copyright (C) 2016 The beasontk Android Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tk.beason.noah.modules.init.guide

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import tk.beason.common.utils.router.AppRouter
import tk.beason.noah.R
import tk.beason.noah.modules.account.login.LoginActivity
import tk.beason.noah.modules.base.AppBaseActivity


/**
 * Created by beasontk on 2018/8/8.
 * 引导页面
 */
class GuideActivity : tk.beason.noah.modules.base.AppBaseActivity(), View.OnTouchListener, View.OnClickListener {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.setOnTouchListener(this)
        viewPager.adapter = GuideAdapter(this, this)
    }

    /**
     * 当前界面不需要显示升级dialog
     */
    override fun needShowUpdateVersionDialog(): Boolean {
        return false
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.come_in -> goToNext()
            R.id.view_pager -> resetStatusBar()
        }

    }

    private fun goToNext() {
        AppRouter.with(context)
                .target(tk.beason.noah.modules.account.login.LoginActivity::class.java)
                .flag(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .finishSelf()
                .start()
    }


    override fun resetStatusBar() {
        val window = window
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE
                )

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }


    private fun hideStatusBar(v: View) {
        v.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            hideStatusBar(v)
        }
        return false
    }

}
