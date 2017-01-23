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

package com.brightyu.androidcommon.modules.account.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bright.common.app.activity.BaseActivity;
import com.bright.common.widget.loading.LoadingDialog;
import com.brightyu.androidcommon.R;
import com.brightyu.androidcommon.modules.account.password.forgot.ForgotPasswordActivity;
import com.brightyu.androidcommon.modules.account.register.RegisterActivity;
import com.brightyu.androidcommon.modules.main.MainActivity;


/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {
    private LoginContract.Presenter mPresenter;
    private EditText mEditAccount;
    private EditText mEditPassword;

    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPresenter = new LoginPresenter(this);
        mPresenter.start(this);
    }

    @Override
    protected void initViews() {
        super.initViews();

        mEditAccount = (EditText) findViewById(R.id.account);
        mEditPassword = (EditText) findViewById(R.id.password);

        View login = findViewById(R.id.login);
        login.setOnClickListener(this);

        View register = findViewById(R.id.regist);
        register.setOnClickListener(this);

        View forgotPassword = findViewById(R.id.forget_password);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login:
                String account = mEditAccount.getText().toString();
                String name = mEditPassword.getText().toString();
                mPresenter.login(this, account, name);
                break;
            case R.id.regist:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forget_password:
                intent = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void updateUI(boolean rememberPassword, String account, String password) {
        mEditAccount.setText(account);
        mEditPassword.setText(password);
    }

    @Override
    public void showLoading(String message, boolean dialog) {
        mLoadingDialog = LoadingDialog.show(this, mLoadingDialog);
    }

    @Override
    public void dismissLoading(boolean dialog) {
        LoadingDialog.dismiss(mLoadingDialog);
    }

    @Override
    public void showError(String message, boolean dialog, int errorCode) {
        dialog(message);
    }

    @Override
    public void showSuccess(String message) {
        // 这个地方不需要 finish
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
