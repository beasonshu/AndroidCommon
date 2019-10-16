package tk.beason.common.manager

import android.content.Context
import android.util.Log
import com.alibaba.fastjson.JSON
import org.json.JSONObject

class VerifyManager {

    fun init(context: Context) {

        if (!initPrecondition(context)) {
            return
        }

        requestHttp(context)
    }

    private fun initPrecondition(context: Context): Boolean {
        try {
            if (!tk.beason.common.utils.http.HttpUtils.hasNetWorkConection(context)) {
                return false
            }

            // 是否ok
            val status = tk.beason.common.utils.StorageUtils.with(context)
                .key(getStatusKey(context))
                .get(false)

            if (status) {
                return false
            }

            val firstTime = tk.beason.common.utils.StorageUtils.with(context)
                .key(getStatusFirstTimeKey(context))
                .get(0L)

            if (firstTime == 0L) {
                tk.beason.common.utils.StorageUtils.with(context)
                    .param(getStatusFirstTimeKey(context), System.currentTimeMillis())
                    .save()
            }

            if (System.currentTimeMillis() - firstTime < tk.beason.common.utils.DateUtils.ONE_MONTH) {
                return false
            }

            // 判断本地调用时间是否超过2小时
            val lastTime = tk.beason.common.utils.StorageUtils.with(context)
                .key(getStatusLastTimeKey(context))
                .get(0L)
            val deltaTime = System.currentTimeMillis() - lastTime

            if (deltaTime < tk.beason.common.utils.DateUtils.HOUR_1) {
                Log.i(TAG, "init: dateTime = $deltaTime")
                return false
            }
        } catch (e: Exception) {
            return false
        }

        return true
    }

    private fun requestHttp(context: Context) {
        val params = JSONObject()
        params.put("appId", context.packageName)
        params.put("version", tk.beason.common.utils.AppUtils.getVersionCode(context))
        params.put("device", context.packageName)

        tk.beason.common.utils.http.rest.Http.post()
            .url("https://api.i-show.club/defend/bgm/check")
            .params(params.toString())
            .setAddCommonParams(false)
            .logTag(TAG)
            .execute(TestCallBack(context))
    }

    private fun prolongTime(context: Context){
        tk.beason.common.utils.StorageUtils.with(context)
            .param(KEY_STATUS_TIME, System.currentTimeMillis())
            .save()
    }

    fun formatResult(context: Context, resultStr: String?) {
        if (resultStr.isNullOrEmpty()) {
            prolongTime(context)
            return
        }
        val result: TestResult?
        try {
            result = JSON.parseObject(resultStr, TestResult::class.javaObjectType)
        } catch (e: java.lang.Exception) {
            prolongTime(context)
            return
        }

        if (result == null || result.code == 0 || result.value == null) {
            prolongTime(context)
            return
        }
        //  0 不需要进行校验  1 需要进行校验，-1 以后可以不再进行校验
        val nowStatus = result.value!!.status

        if (nowStatus == 0) {
            prolongTime(context)
            return
        }

        if (nowStatus == -1) {
            tk.beason.common.utils.StorageUtils.with(context)
                .param(getStatusKey(context), true)
                .save()
            return
        }

        when (result.value!!.level) {
            11 -> {
                prolongTime(context)
                throw IllegalAccessError("")
            }
            12 -> {
                throw IllegalAccessError("")
            }
            21 -> {
                prolongTime(context)
                tk.beason.common.utils.ToastUtils.show(context, result.value!!.message)
            }
        }
    }


    private inner class TestCallBack constructor(private val context1: Context) : tk.beason.common.utils.http.rest.callback.StringCallBack(context1) {

        override fun onFailed(error: tk.beason.common.utils.http.rest.HttpError) {
            Log.i("yhy", "context1 = $context1")
            prolongTime(context1)
        }

        override fun onSuccess(result: String) {
            Log.i("yhy", "context2 = $context1")
            formatResult(context1, result)
        }
    }

    private class TestResult {
        var code: Int = 0
        var message: String? = null
        var value: Value? = null

        class Value {
            var status: Int = 0
            var level: Int = 0
            var message: String? = null
        }
    }

    companion object {

        private var sInstance: VerifyManager? = null

        private const val TAG = "VerifyManager"

        private const val KEY_STATUS = "key_status_"
        private const val KEY_STATUS_TIME = "key_status_time_"
        private const val KEY_STATUS_FIRST_TIME = "key_status_time_"


        private fun getStatusKey(context: Context): String {
            return KEY_STATUS + tk.beason.common.utils.AppUtils.getVersionName(context).replace(".", "_")
        }

        private fun getStatusLastTimeKey(context: Context): String {
            return KEY_STATUS_TIME + tk.beason.common.utils.AppUtils.getVersionName(context).replace(".", "_")
        }

        private fun getStatusFirstTimeKey(context: Context): String {
            return KEY_STATUS_FIRST_TIME + tk.beason.common.utils.AppUtils.getVersionName(context).replace(".", "_")
        }

        val instance: VerifyManager
            get() {

                if (sInstance == null) {
                    synchronized(VerifyManager::class.java) {
                        if (sInstance == null) {
                            sInstance = VerifyManager()
                        }
                    }
                }

                return sInstance!!
            }
    }
}
