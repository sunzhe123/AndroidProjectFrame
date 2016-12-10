package com.hither.androidframe.project.login.view;

/**
 * Created by Administrator on 2016/11/22.
 */
public interface LoginView {
    void setTitleBar(int visibility, String string);

    void setUserNameEditText(String nameText, String nameHint);

    void setPassWordEditText(String passText, String passHint, String type);

    String getUserNameEditText();

    String getPassWordEditText();

    void showLoginNullDialog(boolean nameShow, boolean passShow);
}
