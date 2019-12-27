package com.example.minitumblr.controller;

import android.content.Context;

import com.example.minitumblr.controller.util.Constants;
import com.example.minitumblr.controller.util.SharedPrefs;

public class Controller {

    protected final Context mContext;
    protected final SharedPrefs mSharedPrefs;
    protected long mUserId;

    public Controller(Context context) {
        mContext = context;
        mSharedPrefs = new SharedPrefs(context);
        mUserId = mSharedPrefs.get(Constants.SharedKey.USER_ID, 0L);
    }

    public long getUserId() {
        return mUserId;
    }
}
