package com.example.minitumblr.view;

public interface LoginView extends BaseView {
    void onLoginSuccess();
    void onLoginError(String errorMessage);
}
