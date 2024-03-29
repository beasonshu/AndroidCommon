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

package tk.beason.noah.manager

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.alibaba.fastjson.JSON
import tk.beason.common.utils.AppUtils
import tk.beason.common.utils.StorageUtils
import tk.beason.common.utils.http.rest.Http
import tk.beason.common.utils.http.rest.HttpError
import tk.beason.common.utils.log.LogManager
import tk.beason.noah.entries.Version
import tk.beason.noah.modules.init.splash.SplashActivity
import tk.beason.noah.utils.http.AppHttpCallBack

/**
 * Created by beasontk on 2018/8/8.
 * 版本管理器
 */
class VersionManager private constructor() {
    /**
     * 从服务器中获取到的版本信息
     */
    private var mVersion: Version? = null

    fun init(context: SplashActivity) {
        clear(context.applicationContext)
        isFirstEnterThisVersion = checkIsFirstEnterThisVerison(context.applicationContext)
        getVersionFromServer(context.applicationContext)
        cleanCache(context)
    }


    fun hasNewVersion(context: Context): Boolean {
        val ignore = StorageUtils.with(context)
                .key(Version.Key.IGNORE_NOW)
                .get(false)!!

        if (ignore) {
            Log.i(TAG, "hasNewVersion: already ignore")
            return false
        }

        // 获取当前版本号
        val version = getVersion(context)
        if (version == null) {
            Log.i(TAG, "hasNewVersion: version is null")
            return false
        }

        // 如果当前要升级的版本 比忽略的版本还要低就不进行升级
        val ignoreVersion = getIgnoreVersion(context)
        if (ignoreVersion != null && ignoreVersion.versionCode >= version.versionCode) {
            Log.i(TAG, "hasNewVersion: ignore this version")
            return false
        }

        val versionCode = AppUtils.getVersionCode(context)
        return version.versionCode > versionCode
    }

    fun getVersion(context: Context): Version? {
        if (mVersion == null) {
            val cache = StorageUtils.with(context)
                    .key(Version.Key.CACHE)
                    .get<String>(null)
            makeVersion(cache)
        }
        return mVersion
    }


    private fun getIgnoreVersion(context: Context): Version? {
        val cache = StorageUtils.with(context)
                .key(Version.Key.IGNORE_VERSION)
                .get<String>(null)
        return if (TextUtils.isEmpty(cache)) {
            null
        } else {
            JSON.parseObject(cache, Version::class.java)
        }
    }

    private fun makeVersion(versionJson: String?) {
        if (TextUtils.isEmpty(versionJson)) {
            LogManager.i(TAG, "makeVersion: version is empty")
            return
        }
        mVersion = JSON.parseObject(versionJson, Version::class.java)
    }

    /**
     * 获取版本信息
     */
    private fun getVersionFromServer(context: Context) {
        val version = Version()
        version.versionCode = tk.beason.noah.BuildConfig.VERSION_CODE
        version.versionName = tk.beason.noah.BuildConfig.VERSION_NAME

        Http.post()
                .url("http://10.0.2.55:8080/version/getVersion")
                .params(JSON.toJSONString(version))
                .execute(object : AppHttpCallBack<String>(context) {
                    override fun onFailed(error: HttpError) {

                    }

                    override fun onSuccess(result: String) {
                        val cache = StorageUtils.with(context)
                                .key(Version.Key.CACHE)
                                .get<String>(null)

                        if (!TextUtils.equals(cache, result)) {
                            StorageUtils.with(context)
                                    .key(Version.Key.IGNORE_VERSION)
                                    .remove()
                        }

                        StorageUtils.with(context)
                                .param(Version.Key.CACHE, result)
                                .save()
                        makeVersion(result)
                    }
                })
    }

    private fun cleanCache(context: Context) {
        if (!isFirstEnterThisVersion) {
            return
        }
        tk.beason.noah.manager.CacheManager.Companion.instance!!.clearCache(context)
    }

    companion object {
        private const val TAG = "VersionManager"
        /**
         * 这个东西使用后可以被回收
         */
        @Volatile
        private var sInstance: tk.beason.noah.manager.VersionManager? = null
        /**
         * 是否是第一次进入当前版本
         */
        var isFirstEnterThisVersion: Boolean = false
            private set

        val instance: tk.beason.noah.manager.VersionManager
            get() {

                if (sInstance == null) {
                    synchronized(tk.beason.noah.manager.VersionManager::class.java) {
                        if (sInstance == null) {
                            sInstance =
                                tk.beason.noah.manager.VersionManager()
                        }
                    }
                }

                return sInstance!!
            }

        /**
         * 检测是否是第一次登录这个版本
         */
        private fun checkIsFirstEnterThisVerison(context: Context): Boolean {
            // 获取之前保存的版本信息
            val versionCode = StorageUtils.with(context)
                    .key(AppUtils.VERSION_CODE)
                    .get(0)!!

            val versionName = StorageUtils.with(context)
                    .key(AppUtils.VERSION_NAME)
                    .get<String>(null)

            // 获取当前版本号
            val _versionCode = AppUtils.getVersionCode(context)
            val _versionName = AppUtils.getVersionName(context)
            Log.d(TAG, "originVersion = $versionCode ,localVersion = $_versionCode")
            Log.d(TAG, "originVersionName = $versionName ,localVersionName = $_versionName")

            // 保存现在的版本号
            StorageUtils.with(context)
                    .param(AppUtils.VERSION_CODE, _versionCode)
                    .save()

            StorageUtils.with(context)
                    .param(AppUtils.VERSION_NAME, _versionName)
                    .save()

            // 如果当前版本比保存的版本大，说明APP更新了
            // 版本名称不相等且版本code比上一个版本大 才进行走ViewPager
            return !TextUtils.equals(versionName, _versionName) && _versionCode > versionCode
        }

        /**
         * 清除缓存
         */
        private fun clear(context: Context) {
            StorageUtils.with(context)
                    .key(Version.Key.CACHE)
                    .remove()
            StorageUtils.with(context)
                    .key(Version.Key.IGNORE_NOW)
                    .remove()
        }
    }
}
