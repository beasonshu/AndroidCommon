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

package com.bright.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件夹
 */
public class SelectorFolder {
    public String id;
    public String name;
    public SelectorPicture cover;
    public List<SelectorPicture> images;
    public int count;
    public boolean isSelected;

    public void addImage(SelectorPicture image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(image);
    }

    @Override
    public boolean equals(Object o) {
        try {
            SelectorFolder other = (SelectorFolder) o;
            return this.id.equalsIgnoreCase(other.id);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
