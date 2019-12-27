package com.example.minitumblr.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.minitumblr.R;
import com.example.minitumblr.controller.LoginController;
import com.example.minitumblr.view.LoginView;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.tv_error_message)
    TextView tvErrorMessage;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.cb_legal_terms)
    CheckBox cbLegalTerms;
    @BindView(R.id.tv_legal_terms)
    TextView tvLegalTerms;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private LoginController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mController = new LoginController(this, this);
    }

    @OnTextChanged(value = {R.id.edt_username, R.id.edt_password},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChangedUserCredentials() {
        btnLogin.setEnabled(!TextUtils.isEmpty(edtUsername.getText()) &&
                !TextUtils.isEmpty(edtPassword.getText()));
        tvErrorMessage.setText("");
    }

    @OnClick(R.id.btn_login)
    public void login() {
        btnLogin.setEnabled(false);
        mController.login(edtUsername.getText().toString(), edtPassword.getText().toString());
    }

    @Override
    public void onLoginSuccess() {
        /* Go to Main activity */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginError(String errorMessage) {
        btnLogin.setEnabled(true);
        tvErrorMessage.setText(errorMessage);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        tvErrorMessage.setText("");
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
