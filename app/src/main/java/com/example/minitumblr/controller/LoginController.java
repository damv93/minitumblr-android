package com.example.minitumblr.controller;

import android.content.Context;

import com.example.minitumblr.controller.util.Constants;
import com.example.minitumblr.controller.util.SharedPrefs;
import com.example.minitumblr.view.LoginView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController extends Controller {

    private LoginView mView;
    private static List<Map<String, String>> mockUsers;

    static {
        setMockUsers();
    }

    private static void setMockUsers() {
        mockUsers = new ArrayList<>();

        Map<String, String> user1 = new HashMap<>();
        user1.put("id", "1");
        user1.put("username", "diego93");
        user1.put("password", "123456");

        Map<String, String> user2 = new HashMap<>();
        user2.put("id", "2");
        user2.put("username", "andres15");
        user2.put("password", "123456");

        mockUsers.add(user1);
        mockUsers.add(user2);
    }

    public LoginController(Context context, LoginView view) {
        super(context);
        setView(view);
    }

    public void setView(LoginView view) {
        mView = view;
    }

    public void login(String username, String password) {

        for (Map<String, String> user : mockUsers) {
            if (user.get("username").equals(username) && user.get("password").equals(password)) {
                SharedPrefs sp = new SharedPrefs(mContext);
                sp.set(Constants.SharedKey.USER_ID, Long.parseLong(user.get("id")));
                mView.onLoginSuccess();
                return;
            }
        }
        mView.onLoginError("Credenciales inválidas");
    }
}
