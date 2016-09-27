/**
 * Copyright (C) 2016 The yuhaiyang Android Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bright.common.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.bright.common.R;
import com.bright.common.app.BaseActivity;
import com.bright.common.utils.image.ImageUtils;
import com.bright.common.utils.image.select.SingleSelectPhotoUtils;
import com.bright.common.widget.CropImageView;
import com.bright.common.widget.TopBar;
import com.bright.common.widget.loading.LoadingDialog;
import com.bumptech.glide.Glide;

/**
 * 图片剪切界面
 * 需要使用的Theme  android:theme="@style/Theme.NoActionBar.Fullscreen"
 */
public class CropImageActivity extends BaseActivity {
    private static final String TAG = "CropImageActivity";
    /**
     * 图片路径
     */
    public static final String KEY_PATH = "crop_image_path";
    /**
     * 生成图片的路径
     */
    public static final String KEY_RESULT_PATH = "result_croped_image_path";
    /**
     * x 轴比例
     */
    public static final String KEY_RATIO_X = "result_croped_image_ratio_x";
    /**
     * y 轴比例
     */
    public static final String KEY_RATIO_Y = "result_croped_image_ratio_y";
    private CropImageView mCropView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        Intent intent = getIntent();
        String path = intent.getStringExtra(KEY_PATH);
        Glide.with(this)
                .load(path)
                .asBitmap()
                .into(mCropView);

        int x = intent.getIntExtra(KEY_RATIO_X, 1);
        int y = intent.getIntExtra(KEY_RATIO_Y, 1);
        mCropView.setCustomRatio(x, y);
    }

    @Override
    protected void initViews() {
        super.initViews();
        TopBar topbar = (TopBar) findViewById(R.id.top_bar);
        topbar.setOnTopBarListener(this);

        mCropView = (CropImageView) findViewById(R.id.crop);
    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
        LoadingDialog dialog = LoadingDialog.show(this);
        Bitmap bitmap = mCropView.getCroppedBitmap();
        String cachePath = ImageUtils.compressBitmap(this, bitmap, 300);
        Intent intent = new Intent();
        intent.putExtra(KEY_RESULT_PATH, cachePath);
        setResult(SingleSelectPhotoUtils.Request.REQUEST_CROP, intent);
        dialog.dismiss();
        finish();
    }
}
