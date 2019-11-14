package tk.beason.noah.modules.sample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_evn.*
import tk.beason.common.env.EnvVariable
import tk.beason.noah.AppApplication
import tk.beason.noah.R

class EvnActivity : AppCompatActivity() {

    private val config by lazy { (application as AppApplication).config }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evn)

        tv_config.setOnClickListener { EnvVariable.openConfig(this) }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        val fruitServer = config.fruitServer()
        val animalServer = config.animalServer()

        tv_content.text = """
            ${fruitServer.name}: ${config.fruitServer().value}
            ${animalServer.name}: ${config.animalServer().value}
        """.trimIndent()
    }
}
