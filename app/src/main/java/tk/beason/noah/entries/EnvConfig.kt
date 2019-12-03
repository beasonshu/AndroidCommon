package tk.beason.noah.entries

import tk.beason.common.env.annotation.VariableProp
import tk.beason.common.env.model.Item
import tk.beason.common.env.model.Variable
import tk.beason.noah.BuildConfig

interface EnvConfig {

    class FruitServer {
        class Debug : Item("调试", "https://fruit1.com", true)
        class Release : Item("生产", "https://fruit2.com")

        class Default : Variable.DefaultItemProvider {
            override fun provide() = if (BuildConfig.DEBUG) {
                Debug::class.java
            } else {
                Release::class.java
            }
        }
    }

    @VariableProp(
        name = "fruit", desc = "水果服务器",
        defaultValue = FruitServer.Default::class,
        selections = [FruitServer.Debug::class, FruitServer.Release::class]
    )
    fun fruitServer(): Variable

    class AnimalServer {
        class Debug : Item("调试", "https://animal1.com", true)
        class Release : Item("生产", "https://animal2.com")

        class Default : Variable.DefaultItemProvider {
            override fun provide() = Release::class.java
        }
    }

    @VariableProp(
        name = "animal", desc = "动物服务器",
        defaultValue = AnimalServer.Default::class,
        selections = [AnimalServer.Debug::class, AnimalServer.Release::class]
    )
    fun animalServer(): Variable
}
