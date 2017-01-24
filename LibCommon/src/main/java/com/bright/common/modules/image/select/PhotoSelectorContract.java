/**
 * Copyright (C) 2016 The yuhaiyang Android Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bright.common.modules.image.select;

import com.bright.common.entries.Folder;
import com.bright.common.entries.Photo;
import com.bright.common.mvp.base.BasePresenter;
import com.bright.common.mvp.base.BaseView;

import java.util.List;

/**
 * Created by Bright.Yu on 2017/1/23.
 * 照片选择器的 Contract
 */

class PhotoSelectorContract {
    /**
     * View
     */
    interface View extends BaseView {
        /**
         * 数据都加载出来了 进行显示
         */
        void updateUI(List<Photo> photoList, List<Folder> folderList);
    }

    /**
     * Presenter
     */
    interface Presenter extends BasePresenter {

    }
}