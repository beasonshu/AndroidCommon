package tk.beason.noah

import android.app.Application
import tk.beason.common.env.EnvVariable
import tk.beason.common.utils.http.rest.Http
import tk.beason.common.utils.image.loader.ImageLoader
import tk.beason.common.utils.router.AppRouter
import tk.beason.noah.entries.EnvConfig
import tk.beason.noah.utils.router.AppRouterConfigure


/**
 * Application
 */
class AppApplication : Application() {
    lateinit var config : EnvConfig
    override fun onCreate() {
        super.onCreate()
        config =EnvVariable.register(this, EnvConfig::class.java)
        Http.init(this)
        ImageLoader.init(this)

        AppRouter.setConfigure(AppRouterConfigure())
        EnvVariable.registerRefreshAction({
            // TODO: 2019/1/21 更新 EnvVariable 相关的变量
        })
    }


}
