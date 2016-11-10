package com.hither.androidframe.activity;

import android.content.Context;
import android.view.View;

import com.hither.androidframe.R;
import com.hither.androidframe.tools.LogUtils;
import com.hither.androidframe.widget.CustomEditText;
import com.hither.androidframe.widget.CustomHintDialog;
import com.hither.androidframe.widget.TitleBar;

public class LoginActivity extends BaseActivity {
    private TitleBar login_title;
    private CustomEditText username;
    private CustomEditText password;

    @Override
    public int setContentViewResId() {
        return R.layout.login_layout;
    }

    @Override
    public void initView() {
        LogUtils.i("LoginActivity", "==>LoginActivity context:" + context);
        login_title = (TitleBar) findViewById(R.id.login_title);
        login_title.setBackKeyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        login_title.setRightIconVisibility(View.GONE);
        login_title.setTitleText("登录");
        username = (CustomEditText) findViewById(R.id.username);
        username.setText("帐号：");
        username.setHint("请输入帐号");
        password = (CustomEditText) findViewById(R.id.password);
        password.setText("密码：");
        password.setHint("请输入密码");
        password.setEditTextType("PASSWORD");
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void LoginOnClick(View view) {
        String user = username.getText();
        String pass = password.getText();
        if ("".equals(user)) {
            new CustomHintDialog(this, "请输入帐号").show();
        } else if ("".equals(pass)) {
            new CustomHintDialog(this, "请输入密码").show();
        }
    }
}
