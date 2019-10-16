/*
 * Copyright (C) 2016 The yuhaiyang Android Source Project
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

package tk.beason.noah.manager

import tk.beason.common.widget.watermark.WaterMarkHelp
import tk.beason.noah.modules.init.splash.SplashActivity

/**
 * 配置管理器
 */

class ConfigureManager private constructor() {

    /**
     * 配置文件初始化
     */
    @Suppress("UNUSED_PARAMETER")
    fun init(context: SplashActivity) {

        WaterMarkHelp.show(tk.beason.noah.BuildConfig.VERSION_TYPE != tk.beason.noah.BuildConfig.VERSION_PROD)
        WaterMarkHelp.defaultText(tk.beason.noah.BuildConfig.VERSION_DESCRIPTION)
    }

    companion object {
        private val TAG = "ConfigureManager"
        /**
         * 这个东西使用后可以被回收
         */
        @Volatile
        private var sInstance: tk.beason.noah.manager.ConfigureManager? = null

        val instance: tk.beason.noah.manager.ConfigureManager
            get() {

                if (tk.beason.noah.manager.ConfigureManager.Companion.sInstance == null) {
                    synchronized(tk.beason.noah.manager.ConfigureManager::class.java) {
                        if (tk.beason.noah.manager.ConfigureManager.Companion.sInstance == null) {
                            tk.beason.noah.manager.ConfigureManager.Companion.sInstance =
                                tk.beason.noah.manager.ConfigureManager()
                        }
                    }
                }

                return tk.beason.noah.manager.ConfigureManager.Companion.sInstance!!
            }
    }


}
