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

package tk.beason.common.widget.relativelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import tk.beason.common.R;

/**
 * Created by Bright.Yu on 2016/11/4.
 * 比例的RelativeLayout
 */

public class ScaleRelativeLayout extends RelativeLayout {
    private int mWidthRatio;
    private int mHeightRatio;

    public ScaleRelativeLayout(Context context) {
        this(context, null);
    }

    public ScaleRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScaleRelativeLayout);
        mWidthRatio = a.getInt(R.styleable.ScaleRelativeLayout_widthScale, 1);
        mHeightRatio = a.getInt(R.styleable.ScaleRelativeLayout_heightScale, 1);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int[] measureSpecs;
        if (layoutParams != null && (layoutParams.width > 0 || layoutParams.height > 0)) {
            measureSpecs = measureByLayoutParams(layoutParams, widthMeasureSpec, heightMeasureSpec);
        } else {
            measureSpecs = measureByMeasureSpec(widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(measureSpecs[0], measureSpecs[1]);
    }

    /**
     * 通过LayoutParams 来获取值 进行计算
     * 目的:父类为RelativeLayout 时候MeasureSpec 会错乱
     */
    private int[] measureByLayoutParams(ViewGroup.LayoutParams layoutParams, int widthMeasureSpec, int heightMeasureSpec) {
        if (layoutParams.width > 0) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(layoutParams.width * mHeightRatio / mWidthRatio, MeasureSpec.EXACTLY);
        } else if (layoutParams.height > 0) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(layoutParams.height * mWidthRatio / mHeightRatio, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
        }
        return new int[]{widthMeasureSpec, heightMeasureSpec};
    }

    /**
     * 最原始的measure方法
     */
    private int[] measureByMeasureSpec(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize * mHeightRatio / mWidthRatio, MeasureSpec.EXACTLY);
        } else if (heightMode == MeasureSpec.EXACTLY) {
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize * mWidthRatio / mHeightRatio, MeasureSpec.EXACTLY);
        }
        return new int[]{widthMeasureSpec, heightMeasureSpec};
    }

    public void setRatio(int widthRatio, int heightRatio) {
        mWidthRatio = widthRatio;
        mHeightRatio = heightRatio;
        requestLayout();
    }
}
