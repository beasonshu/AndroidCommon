package tk.beason.noah.manager

import android.content.Context

import java.lang.ref.WeakReference

/**
 * Created by yuhaiyang on 2018/8/8.
 * 缓存管理
 */
class CacheManager private constructor() {

    @Suppress("UNUSED_PARAMETER")
    fun clearCache(context: Context) {
        // TODO
    }

    companion object {

        /**
         * 这个东西使用后可以被回收
         */
        @Volatile
        private var sInstance: tk.beason.noah.manager.CacheManager? = null

        val instance: tk.beason.noah.manager.CacheManager?
            get() {

                if (tk.beason.noah.manager.CacheManager.Companion.sInstance == null) {
                    synchronized(tk.beason.noah.manager.ConfigureManager::class.java) {
                        if (tk.beason.noah.manager.CacheManager.Companion.sInstance == null) {
                            tk.beason.noah.manager.CacheManager.Companion.sInstance =
                                tk.beason.noah.manager.CacheManager()
                        }
                    }
                }

                return tk.beason.noah.manager.CacheManager.Companion.sInstance
            }
    }
}
