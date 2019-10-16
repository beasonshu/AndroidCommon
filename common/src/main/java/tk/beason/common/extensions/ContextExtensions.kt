package tk.beason.common.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast
import tk.beason.common.R

/**
 * Toast提示
 * @param message 内容
 * @param duration 时长
 */
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    tk.beason.common.utils.ToastUtils.show(this, message, duration)
}

/**
 * Toast提示
 * @param message 内容
 * @param duration 时长
 */
fun Context.toast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
    tk.beason.common.utils.ToastUtils.show(this, message, duration)
}

/**
 * Dialog提示
 */
fun Context.dialog(message: Int, finishSelf: Boolean = false, cancelable: Boolean = true) {
    dialog(getString(message), finishSelf, cancelable)
}

/**
 * Dialog提示
 */
fun Context.dialog(message: String, finishSelf: Boolean = false, cancelable: Boolean = true) {
    if (this !is Activity) {
        return
    }
    val activity = this
    val builder = tk.beason.common.widget.dialog.BaseDialog.Builder(this)
    builder.setMessage(message)
    builder.setPositiveButton(R.string.yes) { _, _ -> if (finishSelf) activity.finish() }
    builder.setCancelable(cancelable)
    builder.show()
}

/**
 * Dialog提示
 */
fun Context.dialog(title: Int, message: String, finishSelf: Boolean = false, cancelable: Boolean = true) {
    dialog(getString(title), message, finishSelf, cancelable)
}

/**
 * Dialog提示
 */
fun Context.dialog(title: String, message: String, finishSelf: Boolean = false, cancelable: Boolean = true) {
    if (this !is Activity) {
        return
    }
    val activity = this
    val builder = tk.beason.common.widget.dialog.BaseDialog.Builder(this)
    builder.setMessage(title)
    builder.setMessage(message)
    builder.setPositiveButton(R.string.yes) { _, _ -> if (finishSelf) activity.finish() }
    builder.setCancelable(cancelable)
    builder.show()
}

