package tk.beason.noah

import android.app.Application
import tk.beason.common.utils.http.rest.Http
import tk.beason.common.utils.image.loader.ImageLoader
import tk.beason.common.utils.router.AppRouter
import tk.beason.noah.utils.router.AppRouterConfigure


/**
 * Application
 */
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Http.init(this)
        ImageLoader.init(this)

        AppRouter.setConfigure(AppRouterConfigure())
    }
}
