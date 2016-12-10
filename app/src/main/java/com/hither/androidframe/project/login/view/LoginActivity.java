package com.hither.androidframe.project.login.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.Window;

import com.hither.androidframe.R;
import com.hither.androidframe.project.BaseActivity;
import com.hither.androidframe.project.login.presenter.LoginPresenter;
import com.hither.androidframe.tools.LogUtils;
import com.hither.androidframe.widget.CustomEditText;
import com.hither.androidframe.widget.CustomHintDialog;
import com.hither.androidframe.widget.TitleBar;

public class LoginActivity extends BaseActivity implements LoginView {
    private TitleBar login_title;
    private CustomEditText username;
    private CustomEditText password;
    private LoginPresenter loginPresenter;
    private View login_layout;
    private ActivityOptionsCompat compat;

    @Override
    public int setContentViewResId() {
        return R.layout.login_layout;
    }

    @Override
    public void initView() {
        login_layout = $(R.id.login_layout);
        login_title = $(R.id.login_title);
        username = $(R.id.username);
        password = $(R.id.password);
        loginPresenter = new LoginPresenter(this);
        loginPresenter.setTitleBar(View.GONE, "登录");
        loginPresenter.setUserNameEditText("帐号:", "请输入帐号");
        loginPresenter.setPassWordEditText("密码:", "请输入密码", "PASSWORD");
    }

    @Override
    public Context getContext() {
        return this;
    }

    public void LoginOnClick(View view) {
        loginPresenter.checkLoginNull();
    }

    @Override
    public void setTitleBar(int visibility, String string) {
        int color = getResources().getColor(R.color.palevioletred2);
        login_title.setBackgroundColor(color);
        login_title.setBackKeyClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_out_top);
            }
        });
        login_title.setRightIconVisibility(visibility);
        login_title.setTitleText(string);
    }

    @Override
    public void setUserNameEditText(String nameText, String nameHint) {
        username.setText(nameText);
        username.setHint(nameHint);
    }

    @Override
    public void setPassWordEditText(String passText, String passHint, String type) {
        password.setText(passText);
        password.setHint(passHint);
        password.setEditTextType(type);
    }

    @Override
    public String getUserNameEditText() {
        return username.getText();
    }

    @Override
    public String getPassWordEditText() {
        return password.getText();
    }

    @Override
    public void showLoginNullDialog(boolean nameShow, boolean passShow) {
        if (nameShow) {
            CustomHintDialog dialog = new CustomHintDialog(this, "请输入帐号");
            Window window = dialog.getWindow();//设置动画
//            window.setGravity(Gravity.CENTER);//设置对话框的显示位置
            window.setWindowAnimations(R.style.mystyle);
            dialog.show();
        } else if (passShow) {
            CustomHintDialog dialog = new CustomHintDialog(this, "请输入密码");
            Window window = dialog.getWindow();
            window.setWindowAnimations(R.style.mystyle);
            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_out_top);
//        overridePendingTransition方法的第一个参数enterAnim是指进入Activity的动画，第二个参数exitAnim是指当前Activity的退出动画

    }
}
