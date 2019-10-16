package tk.beason.common.extensions


/**
 * 解析成人民币
 */
fun String.format2Money(scale: Int = -1, force: Boolean = false): String {
    if (this.isEmpty()) {
        return tk.beason.common.utils.StringUtils.MONEY + tk.beason.common.utils.StringUtils.BLANK + "0"
    }

    return if (scale > 0) {
        val money = tk.beason.common.utils.MathUtils.rounding(this, scale, force)
        tk.beason.common.utils.StringUtils.MONEY + tk.beason.common.utils.StringUtils.BLANK + money
    } else {
        tk.beason.common.utils.StringUtils.MONEY + tk.beason.common.utils.StringUtils.BLANK + this
    }
}
