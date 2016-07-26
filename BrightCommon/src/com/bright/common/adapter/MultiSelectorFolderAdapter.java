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

package com.bright.common.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bright.common.R;
import com.bright.common.model.MultiSelectorFolder;
import com.bumptech.glide.Glide;


/**
 * 文件夹Adapter
 */
public class MultiSelectorFolderAdapter extends ListAdapter<MultiSelectorFolder, MultiSelectorFolderAdapter.ViewHolder> {


    int mSelectedItem = 0;

    public MultiSelectorFolderAdapter(Context context) {
        super(context);
    }

    @Override
    public int getHeaderCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_BODY;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(int position, int type) {
        View view = mLayoutInflater.inflate(R.layout.item_multi_select_folder, null);
        return new ViewHolder(view, type);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, int type) {
        if (type == TYPE_HEADER) {
            holder.name.setText(R.string.folder_all);
            holder.size.setText(mContext.getString(R.string.link_sheet, getTotalImageSize()));

            MultiSelectorFolder f = getItem(0);
            Glide.with(mContext)
                    .load(f.cover.path)
                    .error(R.drawable.no_picture)
                    .centerCrop()
                    .crossFade()
                    .into(holder.cover);
        } else {
            
            MultiSelectorFolder entry = getRealItem(position);
            holder.name.setText(entry.name);
            holder.size.setText(mContext.getString(R.string.link_sheet, entry.images.size()));
            // 显示图片
            Glide.with(mContext)
                    .load(entry.cover.path)
                    .placeholder(R.drawable.no_picture)
                    .centerCrop()
                    .crossFade()
                    .into(holder.cover);
        }

        if (mSelectedItem == position) {
            holder.indicator.setVisibility(View.VISIBLE);
        } else {
            holder.indicator.setVisibility(View.INVISIBLE);
        }
    }

    private int getTotalImageSize() {
        int result = 0;
        for (MultiSelectorFolder f : getData()) {
            result += f.images.size();
        }
        return result;
    }

    public void setSelectIndex(int i) {
        if (mSelectedItem == i) return;

        mSelectedItem = i;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return mSelectedItem;
    }

    public class ViewHolder extends ListAdapter.Holder {
        ImageView cover;
        TextView name;
        TextView size;
        ImageView indicator;

        public ViewHolder(View item, int type) {
            super(item, type);
            cover = (ImageView) item.findViewById(R.id.cover);
            name = (TextView) item.findViewById(R.id.name);
            size = (TextView) item.findViewById(R.id.size);
            indicator = (ImageView) item.findViewById(R.id.state);
        }
    }

}
