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

package tk.beason.common.widget.loading;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import tk.beason.common.R;
import tk.beason.common.utils.DeviceUtils;


public class LoadingDialog extends Dialog {

    private LoadingDialog(Context context) {
        this(context, R.style.Theme_Dialog_Semipermeable);
    }

    private LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private static LoadingDialog show(Context context) {
        if (!(context instanceof Activity)) {
            return null;
        }

        Activity activity = (Activity) context;
        if (activity.isFinishing()) {
            return null;
        }

        LoadingDialog loadingDialog = new LoadingDialog(context);
        loadingDialog.show();
        return loadingDialog;
    }

    public static LoadingDialog show(Context context, LoadingDialog dialog) {
        // 如果Dialog不存在那么就直接new一个
        if (dialog == null) {
            return show(context);
        }
        // 没有在显示就直接显示，如果已经在显示了 那么什么也不干
        if (!dialog.isShowing()) {
            dialog.show();
        }
        return dialog;
    }

    public static void dismiss(LoadingDialog dialog) {
        if (dialog == null) {
            return;
        }
        if(dialog.isShowing()) { //check if dialog is showing.
            //get the Context object that was used to great the dialog
            Context context = ((ContextWrapper)dialog.getContext()).getBaseContext();

            //if the Context used here was an activity AND it hasn't been finished or destroyed
            //then dismiss it
            if(context instanceof Activity) {
                if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed())
                    dialog.dismiss();
            } else{
                //if the Context used wasnt an Activity, then dismiss it too
                dialog.dismiss();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("dialog_loading");// for ddms show
        setContentView(R.layout.dialog_loading);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        if (window == null) {
            return;
        }

        WindowManager.LayoutParams lp = window.getAttributes();
        final int screen[] = DeviceUtils.getScreenSize();
        lp.width = screen[0];
        lp.height = screen[1];

        window.setWindowAnimations(R.style.Animation_Windows_Alpha);
        window.setAttributes(lp);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }
}
