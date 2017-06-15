/*
 * Copyright (C) 2016 The yuhaiyang Android Source Project
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

package com.ishow.common.utils.image.loader.glide;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ishow.common.R;
import com.ishow.common.utils.glide.transform.GlideCircleTransform;
import com.ishow.common.utils.image.loader.IImageLoaderExecutor;
import com.ishow.common.utils.image.loader.ImageLoader;
import com.ishow.common.utils.image.loader.ImageLoaderParams;

/**
 * Created by Bright on 2017/4/10.
 * Glide 加载图片
 */
public class GlideImageLoaderExecutor implements IImageLoaderExecutor {
    @Override
    public void display(@NonNull ImageLoaderParams params, @NonNull ImageView view) {
        params.checkValid();

        DrawableTypeRequest<String> request = Glide.with(params.getContext())
                .load(params.getUrl());

        // 配置特殊加载
        setPlan(request, params);
        // 加载监听
        setListener(request, params);

        // 设置加载的占位
        if (params.getPlaceholderDrawable() != null) {
            request.placeholder(params.getPlaceholderDrawable());
        } else if (params.getPlaceholderRes() > 0) {
            request.placeholder(params.getPlaceholderRes());
        }

        // 设置加载失败时候的显示
        if (params.getErrorDrawable() != null) {
            request.error(params.getErrorDrawable());
        } else if (params.getErrorRes() > 0) {
            request.error(params.getErrorRes());
        }

        // 设置加载的模式
        switch (params.getMode()) {
            case ImageLoader.LoaderMode.CENTER_CROP:
                request.centerCrop();
                break;
            case ImageLoader.LoaderMode.FIT_CENTER:
                request.fitCenter();
                break;
        }


        request.crossFade();
        request.into(view);
    }


    /**
     * 目的是有些特殊加载可以通过设置一个plan来特殊处理
     */
    private void setPlan(DrawableTypeRequest<String> request, final ImageLoaderParams params) {
        switch (params.getPlan()) {
            case ImageLoader.Plan.NORMAL:
                request.centerCrop();
                request.crossFade();
                request.placeholder(R.drawable.no_picture);
                break;
            case ImageLoader.Plan.CIRCLE:
                request.bitmapTransform(new GlideCircleTransform(params.getContext()));
                request.crossFade();
                break;
        }
    }


    /**
     * 设置回调监听
     */
    private void setListener(DrawableTypeRequest<String> request, final ImageLoaderParams params) {
        if (params.getImageLoaderListener() == null) {
            return;
        }

        request.listener(new RequestListener<String, GlideDrawable>() {

            @Override
            public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                params.getImageLoaderListener().onFailed();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                params.getImageLoaderListener().onSuccess(glideDrawable);
                return false;
            }
        });
    }
}