/*
 * Copyright (C) 2017. The beasontk Android Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tk.beason.noah.modules.egg

import android.content.Context
import tk.beason.noah.R
import tk.beason.noah.modules.egg.detail.EggAppInfoActivity
import java.util.*

/**
 * Created by beasontk on 2017/6/5.
 * 工厂类
 */

object EggFactory {

    fun product(context: Context): List<tk.beason.noah.entries.egg.Egg> {
        val eggList = ArrayList<tk.beason.noah.entries.egg.Egg>()
        val egg = tk.beason.noah.entries.egg.Egg()
        egg.name = context.getString(R.string.egg_app_info)
        egg.action = EggAppInfoActivity::class.java
        eggList.add(egg)
        return eggList
    }
}
