/*
 * Copyright (C) 2016 The beasontk Android Source Project
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

package tk.beason.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;

/**
 * 屏幕工具
 */
public class ScreenUtils {

    /**
     * 获取屏幕的宽和高
     *
     * @deprecated 请使用 {@link DeviceUtils#getScreenSize()}  }
     */
    @Deprecated
    public static int[] getScreenSize(Context context) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

    /**
     * 获取屏幕的宽和高
     *
     * @deprecated 请使用 {@link DeviceUtils#getScreenSize()}  }
     */
    @Deprecated
    public static int[] getScreenSize() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

    /**
     * 获取状态栏的高度
     *
     * @deprecated 请使用 {@link DeviceUtils#getScreenSize()}  }
     */
    @Deprecated
    public static int getStatusBarHeight(Activity context) {
        Rect rect = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }
}
