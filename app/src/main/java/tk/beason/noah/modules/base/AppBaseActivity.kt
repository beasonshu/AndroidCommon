package tk.beason.noah.modules.base

import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import tk.beason.common.app.activity.BaseActivity
import tk.beason.common.widget.watermark.WaterMarkView
import tk.beason.noah.ui.widget.dialog.VersionDialog


/**
 * Created by yuhaiyang on 2018/8/8.
 * App层面的BaseActivity
 */
abstract class AppBaseActivity : BaseActivity() {

    /**
     * 检测升级的Dialog
     */
    private var mVersionDialog: Dialog? = null

    /**
     * 获取应用的Application
     */
    @Suppress("unused")
    protected val appApplication: tk.beason.noah.AppApplication
        get() = application as tk.beason.noah.AppApplication


    override fun initViews() {
        super.initViews()
        WaterMarkView.attachToActivity(this)
    }

    override fun initViews(view: View) {
        super.initViews(view)
        WaterMarkView.attachToActivity(this)
    }

    override fun initViews(view: View, params: ViewGroup.LayoutParams) {
        super.initViews(view, params)
        WaterMarkView.attachToActivity(this)
    }


    override fun onResume() {
        super.onResume()


        if (needShowUpdateVersionDialog() && tk.beason.noah.manager.VersionManager.instance.hasNewVersion(context)) {
            showVersionDialog()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        dismissVersionDialog()
    }

    /**
     * 是否需要显示升级dialog
     */
    protected open fun needShowUpdateVersionDialog(): Boolean {
        return true
    }

    /**
     * 显示升级Dialog
     */
    private fun showVersionDialog() {
        if (mVersionDialog == null) {
            mVersionDialog = VersionDialog(this)
        }

        if (!mVersionDialog!!.isShowing) {
            mVersionDialog!!.show()
        }
    }

    /**
     * 隐藏升级的Dialog
     */
    private fun dismissVersionDialog() {
        if (mVersionDialog != null && mVersionDialog!!.isShowing) {
            mVersionDialog!!.dismiss()
        }
        mVersionDialog = null
    }
}
