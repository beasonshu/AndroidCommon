package tk.beason.noah.modules.init.splash

import android.Manifest
import android.content.Context
import android.text.TextUtils

import com.alibaba.fastjson.JSONObject
import com.baidu.mobstat.StatService
import tk.beason.common.utils.DeviceUtils
import tk.beason.common.utils.http.rest.Http
import tk.beason.common.utils.http.rest.HttpError
import tk.beason.common.utils.permission.PermissionManager
import tk.beason.noah.constant.Configure
import tk.beason.noah.constant.Url
import tk.beason.noah.entries.UserContainer
import tk.beason.noah.manager.ConfigureManager
import tk.beason.noah.manager.UserManager
import tk.beason.noah.manager.VersionManager
import tk.beason.noah.modules.base.AppBaseActivity
import tk.beason.noah.utils.http.AppHttpCallBack

/**
 * Created by yuhaiyang on 2018/3/27.
 * Presenter
 */

internal class SplashPresenter(private val mView: SplashContract.View) : SplashContract.Presenter {

    companion object {
        /**
         * 请求权限的Code
         */
        const val REQUEST_PERMISSION_CODE = 1001
        /**
         * 请求的权限
         */
        private val PERMISSIONS = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    /**
     * 初始化的时间
     */
    override var initStartTime: Long = 0
    /**
     * 登录完成
     */
    override var isInitFinished: Boolean = false

    private var mLoginSuccess: Boolean = false


    override val target: Target
        get() {
            if (tk.beason.noah.manager.VersionManager.isFirstEnterThisVersion) {
                return Target.GUIDE
            }
            return if (mLoginSuccess) Target.MAIN else Target.LOGIN
        }

    override fun preInit(context: Context) {
        // 百度统计 - 进入就开始初始化
        StatService.start(context)
        initStartTime = System.currentTimeMillis()
        isInitFinished = false
        mLoginSuccess = false
    }

    override fun checkPermission(activity: tk.beason.noah.modules.base.AppBaseActivity) {
        if (PermissionManager.hasPermission(activity, *PERMISSIONS)) {
            mView.permissionGranted()
        } else {
            PermissionManager.with(activity)
                    .permission(*PERMISSIONS)
                    .annotationClass(activity)
                    .requestCode(REQUEST_PERMISSION_CODE)
                    .send()
        }
    }

    override fun init(activity: SplashActivity) {
        initStartTime = System.currentTimeMillis()
        isInitFinished = false
        mLoginSuccess = false
        // 配置管理
        tk.beason.noah.manager.ConfigureManager.instance.init(activity)
        // 更新版本信息
        tk.beason.noah.manager.VersionManager.instance.init(activity)
        // 登录
        login(activity)
    }


    /**
     * 验证登录
     */
    private fun login(context: Context) {
        val accessToken = tk.beason.noah.manager.UserManager.instance.getAccessToken(context)
        if (TextUtils.isEmpty(accessToken)) {
            isInitFinished = true
            mView.next()
            return
        }

        val params = JSONObject()
        params["device"] = tk.beason.noah.constant.Configure.DEVICE
        params["deviceModel"] = DeviceUtils.model()
        params["deviceVersion"] = DeviceUtils.version()

        Http.post()
                .url(tk.beason.noah.constant.Url.loginByToken())
                .params(params.toJSONString())
                .addHeader(tk.beason.noah.constant.Configure.HTTP_TOKEN, accessToken)
                .execute(object : AppHttpCallBack<tk.beason.noah.entries.UserContainer>(context) {
                    override fun onFailed(error: HttpError) {
                        isInitFinished = true
                        mLoginSuccess = false
                        mView.next()
                    }

                    override fun onSuccess(result: tk.beason.noah.entries.UserContainer) {
                        val userManager = tk.beason.noah.manager.UserManager.instance
                        userManager.setUserContainer(context, result)
                        isInitFinished = true
                        mLoginSuccess = true
                        mView.next()
                    }
                })
    }


    enum class Target {
        /**
         * 主页
         */
        MAIN,
        /**
         * 登录页
         */
        LOGIN,
        /**
         * 引导页
         */
        GUIDE
    }


}
