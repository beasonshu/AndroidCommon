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

package com.ishow.noahark.modules.account.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.ishow.common.app.activity.BaseActivity;
import com.ishow.common.utils.SharedPreferencesUtils;
import com.ishow.common.utils.StringUtils;
import com.ishow.common.widget.edittext.EditTextPro;
import com.ishow.common.widget.loading.LoadingDialog;
import com.ishow.noahark.R;
import com.ishow.noahark.entries.User;
import com.ishow.noahark.modules.account.password.forgot.ForgotPasswordActivity;
import com.ishow.noahark.modules.account.register.RegisterActivity;
import com.ishow.noahark.modules.main.MainActivity;


/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener {
    private LoginContract.Presenter mPresenter;
    private EditTextPro mEditAccount;
    private EditTextPro mEditPassword;

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

        mEditAccount = (EditTextPro) findViewById(R.id.account);
        mEditAccount.addInputWatcher(mTextWatcher);
        mEditPassword = (EditTextPro) findViewById(R.id.password);

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
                String account = mEditAccount.getInputText();
                String name = mEditPassword.getInputText();
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
        mEditAccount.setInputText(account);
        mEditPassword.setInputText(password);
    }


    @Override
    public void showSuccess(String message) {
        // 这个地方不需要 finish
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        SharedPreferencesUtils.save(this, User.Key.AUTO_LOGIN, true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEditAccount.removeInputWatcher(mTextWatcher);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mEditPassword.setInputText(StringUtils.EMPTY);
        }
    };
}
