package com.hither.androidframe.project.login.presenter;

import com.hither.androidframe.project.login.view.LoginView;

/**
 * Created by Administrator on 2016/11/22.
 */
public class LoginPresenter {
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    public void setTitleBar(int visibility, String string) {
        loginView.setTitleBar(visibility,string);
    }

    public void setUserNameEditText(String nameText, String nameHint) {
        loginView.setUserNameEditText(nameText, nameHint);
    }

    public void setPassWordEditText(String passText, String passHint, String type) {
        loginView.setPassWordEditText(passText, passHint, type);
    }

    public void checkLoginNull() {
        boolean f1 = false;
        boolean f2 = false;
        if ("".equals(loginView.getUserNameEditText())) {
            f1 = true;
        }
        if ("".equals(loginView.getPassWordEditText())) {
            f2 = true;
        }
        loginView.showLoginNullDialog(f1, f2);
//        loginView.showLoginDialog()
    }
}
