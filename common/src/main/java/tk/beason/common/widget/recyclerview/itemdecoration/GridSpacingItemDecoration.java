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

package tk.beason.common.widget.recyclerview.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * 只进行增加空边距的
 * <p>
 * 注意：
 * 1. 目前仅仅支持 GridLayoutManager
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    /**
     * Item间距
     */
    private int mSpacing;

    public GridSpacingItemDecoration(Context context, @DimenRes int spacing) {
        mSpacing = context.getResources().getDimensionPixelSize(spacing) / 2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(mSpacing, mSpacing, mSpacing, mSpacing);
    }
}