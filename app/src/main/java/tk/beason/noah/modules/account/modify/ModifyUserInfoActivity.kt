package tk.beason.noah.modules.account.modify

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import tk.beason.common.utils.image.loader.ImageLoader
import tk.beason.common.utils.image.select.OnSelectPhotoListener
import tk.beason.common.utils.image.select.SelectPhotoUtils
import tk.beason.noah.R
import tk.beason.noah.manager.UserManager
import tk.beason.noah.modules.base.AppBaseActivity
import kotlinx.android.synthetic.main.activity_modify_user_info.*

/**
 * Created by yuhaiyang on 2018/8/8.
 * 修改用户信息
 */
class ModifyUserInfoActivity : tk.beason.noah.modules.base.AppBaseActivity(),
    tk.beason.noah.modules.account.modify.ModifyUserInfoContract.View,
        OnSelectPhotoListener,
        View.OnClickListener {


    private lateinit var mPresenter: tk.beason.noah.modules.account.modify.ModifyUserInfoContract.Presenter
    private lateinit var mSelectPhotoUtils: SelectPhotoUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_user_info)
        mPresenter = tk.beason.noah.modules.account.modify.ModifyUserInfoPresenter(this)

        mSelectPhotoUtils = SelectPhotoUtils(this, SelectPhotoUtils.SelectMode.SINGLE)
        mSelectPhotoUtils.setOnSelectPhotoListener(this)
    }

    override fun initViews() {
        super.initViews()
        val userManager = tk.beason.noah.manager.UserManager.instance
        header.setRightImageUrl2(userManager.getAvatar(this), ImageLoader.LoaderMode.CIRCLE_CROP)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mSelectPhotoUtils.onActivityResult(requestCode, resultCode, data)
    }

    override fun updateAvatar(avatar: String) {
        header.setRightImageUrl2(avatar, ImageLoader.LoaderMode.CIRCLE_CROP)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.header -> selectPhoto()
        }
    }

    private fun selectPhoto() {
        mSelectPhotoUtils.setSelectMode(SelectPhotoUtils.SelectMode.SINGLE)
        mSelectPhotoUtils.select(1, 1, Bitmap.CompressFormat.JPEG)
    }

    override fun onSelectedPhoto(multiPath: List<String>, singlePath: String) {
        mPresenter.modifyAvatar(this, singlePath)
    }
}