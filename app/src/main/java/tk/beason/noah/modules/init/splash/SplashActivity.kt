package tk.beason.noah.modules.init.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import tk.beason.common.utils.permission.PermissionDenied
import tk.beason.common.utils.permission.PermissionGranted
import tk.beason.common.utils.router.AppRouter
import tk.beason.noah.constant.Configure
import tk.beason.noah.modules.base.AppBaseActivity
import tk.beason.noah.modules.main.MainActivity
import tk.beason.noah.modules.sample.main.SampleMainActivity

/**
 * Created by yuhaiyang on 2018/3/27.
 * Splash
 */
class SplashActivity : tk.beason.noah.modules.base.AppBaseActivity(), SplashContract.View {
    private lateinit var mPresenter: SplashContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHandler = Handler()
        mPresenter = SplashPresenter(this)
        mPresenter.preInit(this)
        mPresenter.checkPermission(this)
    }

    override fun onResume() {
        super.onResume()
        if (mPresenter.isInitFinished) {
            // 稍微延迟一下,体验更好
            nextDelay(800)
        }
    }

    /**
     * 权限已经获取到
     */
    @PermissionGranted(SplashPresenter.REQUEST_PERMISSION_CODE)
    override fun permissionGranted() {
        mPresenter.init(this@SplashActivity)
    }


    /**
     * 权限已经被拒绝
     */
    @Suppress("unused")
    @PermissionDenied(SplashPresenter.REQUEST_PERMISSION_CODE)
    fun permissionDenied() {
        finish()
    }


    /**
     * 延迟进入
     */
    private fun nextDelay(time: Long) {
        mHandler.postDelayed({ next() }, time)
    }

    /**
     * 下一步
     */
    override fun next() {
        if (isActivityPaused) {
            return
        }

        if (!mPresenter.isInitFinished) {
            return
        }

        val walkedTime = System.currentTimeMillis() - mPresenter.initStartTime
        if (walkedTime < tk.beason.noah.constant.Configure.SPLASH_TIME) {
            nextDelay(tk.beason.noah.constant.Configure.SPLASH_TIME - walkedTime)
            return
        }

        when (mPresenter.target) {
            SplashPresenter.Target.MAIN -> {
                AppRouter.with(this)
                        .target(SampleMainActivity::class.java)!!
                        .finishSelf()!!
                        .start()
            }
            SplashPresenter.Target.LOGIN -> {
                AppRouter.with(this)
                        .target(SampleMainActivity::class.java)!!
                        .finishSelf()!!
                        .start()
            }
            SplashPresenter.Target.GUIDE -> {
                AppRouter.with(this)
                        .target(SampleMainActivity::class.java)!!
                        .finishSelf()!!
                        .start()
            }
        }
    }


    // ======= 系统配置 ===========//
    /**
     * 设置StatusBar的样式
     */
    override fun resetStatusBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
    }

    /**
     * 是否显示版本更新的Dialog
     */
    override fun needShowUpdateVersionDialog(): Boolean {
        return false
    }
}
