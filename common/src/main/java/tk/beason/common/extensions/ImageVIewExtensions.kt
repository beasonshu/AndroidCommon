package tk.beason.common.extensions

import android.text.TextUtils
import android.widget.ImageView

/**
 * 加载图片Url
 */
fun ImageView.loadUrl(url: String?, mode: Int = tk.beason.common.utils.image.loader.ImageLoader.LoaderMode.CENTER_CROP, placeHolder: Int = android.R.color.transparent) {
    if (TextUtils.isEmpty(url)) {
        return
    }
    tk.beason.common.utils.image.loader.ImageLoader.with(context)
            .load(url!!)
            .mode(mode)
            .placeholder(placeHolder)
            .into(this)
}