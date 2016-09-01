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

package com.brightyu.androidcommon.modules.register;

import android.os.Bundle;
import android.view.View;

import com.bright.common.widget.TopBar;
import com.bright.common.widget.VerifyCodeButton;
import com.bright.common.widget.loading.LoadingDialog;
import com.brightyu.androidcommon.R;
import com.brightyu.androidcommon.modules.base.AppBaseActivity;
import com.brightyu.androidcommon.ui.widget.InputEdit;

/**
 * 注册界面
 */
public class RegisterActivity extends AppBaseActivity implements View.OnClickListener, RegisterContract.View {

    private InputEdit mInputPhone;
    private InputEdit mInputVerify;
    private InputEdit mInputPassword;
    private InputEdit mInputPasswordEnsure;

    private VerifyCodeButton mVerifyCodeButton;

    private RegisterContract.Presenter mPresenter;

    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mPresenter = new RegisterPresenter(this, this);
    }

    @Override
    protected void initViews() {
        super.initViews();
        TopBar topBar = (TopBar) findViewById(R.id.top_bar);
        topBar.setOnTopBarListener(this);

        mInputPhone = (InputEdit) findViewById(R.id.phone);
        mInputVerify = (InputEdit) findViewById(R.id.verify_code);
        mInputPassword = (InputEdit) findViewById(R.id.password);
        mInputPasswordEnsure = (InputEdit) findViewById(R.id.ensure_password);

        mVerifyCodeButton = (VerifyCodeButton) findViewById(R.id.send_verify_code);
        mVerifyCodeButton.setOnClickListener(this);

        View register = findViewById(R.id.submit);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_verify_code:
                mVerifyCodeButton.showLoading();
                mPresenter.sendVerifiyCode();
                break;
            case R.id.submit:
                String phone = mInputPhone.getInputText();
                String verifyCode = mInputVerify.getInputText();
                String password = mInputPassword.getInputText();
                String passwordEnsure = mInputPasswordEnsure.getInputText();
                mPresenter.register(phone, verifyCode, password, passwordEnsure);
                break;
        }
    }

    @Override
    public void showRegistering() {
        mLoadingDialog = LoadingDialog.show(this);
    }

    @Override
    public void showRegisterFail(String message) {
        LoadingDialog.dismiss(mLoadingDialog);
        dialog(message);
    }

    @Override
    public void showRegisterSuccess() {
        LoadingDialog.dismiss(mLoadingDialog);
    }

    @Override
    public void showSendVerifySuccess() {
        mVerifyCodeButton.startTiming();
    }

    @Override
    public void showSendVerifyFail(String message) {
        mVerifyCodeButton.reset();
        dialog(message);
    }
}