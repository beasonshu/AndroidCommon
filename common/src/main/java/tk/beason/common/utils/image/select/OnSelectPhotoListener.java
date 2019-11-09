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

package tk.beason.common.utils.image.select;

import java.util.List;

/**
 * Created by Bright.Yu on 2017/1/15.
 * 选择图片的监听
 */

public interface OnSelectPhotoListener {
    /**
     * @param multiPath  多选图片的路径
     * @param singlePath 单选图片
     */
    void onSelectedPhoto(List<String> multiPath, String singlePath);
}
