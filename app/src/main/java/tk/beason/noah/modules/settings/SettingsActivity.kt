/*
 * Copyright (C) 2016 The beasontk Android Source Project
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

package tk.beason.noah.modules.settings

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import tk.beason.common.utils.AppUtils
import tk.beason.common.utils.router.AppRouter
import tk.beason.common.widget.TopBar
import tk.beason.common.widget.dialog.BaseDialog
import tk.beason.noah.modules.account.login.LoginActivity
import kotlinx.android.synthetic.main.activity_settings.*
import tk.beason.noah.R
import tk.beason.noah.modules.base.AppBaseActivity
import tk.beason.noah.modules.egg.EggActivity

/**
 * Created by beasontk on 2017/4/24.
 * 设置
 */

class SettingsActivity : AppBaseActivity(), TopBar.OnSecretListener, View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }


    override fun initViews() {
        super.initViews()

        mTopBar.setOnSecretListener(this)
        logout.setOnClickListener(this)
        version.setText(AppUtils.getVersionName(this))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.logout -> logout()
        }
    }

    private fun logout() {
        BaseDialog.Builder(this)
                .setMessage(R.string.ensure_logout)
                .setMessageGravity(Gravity.CENTER)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.yes){ _, _ ->
                    AppRouter.with(this)
                        .target(tk.beason.noah.modules.account.login.LoginActivity::class.java)
                        .flag(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        .finishSelf()
                        .start()}
                .show()
    }


    override fun onSecretClick(v: View, count: Int) {
        if (count == 5) {
            val intent = Intent(this, EggActivity::class.java)
            startActivity(intent)
        }
    }
}
