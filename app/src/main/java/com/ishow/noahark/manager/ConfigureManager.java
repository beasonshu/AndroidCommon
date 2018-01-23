/**
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

package com.ishow.noahark.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.ishow.common.utils.AppUtils;
import com.ishow.common.utils.SharedPreferencesUtils;
import com.ishow.common.utils.http.rest.Http;
import com.ishow.common.utils.http.rest.HttpError;
import com.ishow.common.utils.log.L;
import com.ishow.common.widget.watermark.WaterMarkHelp;
import com.ishow.noahark.BuildConfig;
import com.ishow.noahark.entries.Version;
import com.ishow.noahark.modules.init.SplashActivity;
import com.ishow.noahark.utils.http.AppHttpCallBack;

import java.lang.ref.WeakReference;

/**
 * 配置管理器
 */

public class ConfigureManager {
    private static final String TAG = "ConfigureManager";
    /**
     * 这个东西使用后可以被回收
     */
    private volatile static WeakReference<ConfigureManager> sIntance;


    private ConfigureManager() {
    }

    public static ConfigureManager getInstance() {

        if (sIntance == null || sIntance.get() == null) {
            synchronized (ConfigureManager.class) {
                if (sIntance == null || sIntance.get() == null) {
                    ConfigureManager manager = new ConfigureManager();
                    sIntance = new WeakReference<>(manager);
                }
            }
        }

        return sIntance.get();
    }

    public void init(SplashActivity context) {
        WaterMarkHelp.show(BuildConfig.VERSION_TYPE != BuildConfig.VERSION_PROD);
        WaterMarkHelp.defaultText(BuildConfig.VERSION_DESCRIPTION);
    }


}