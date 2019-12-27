package com.example.minitumblr;

import android.app.Application;

import com.example.minitumblr.data.entity.DaoMaster;
import com.example.minitumblr.data.entity.DaoSession;

import org.greenrobot.greendao.database.Database;

public class MiniTumblrApp extends Application implements MiniTumblrAppData {
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    public void initGreenDao() {
        boolean isBDEncrypted = getApplicationContext().getResources().getBoolean(R.bool.is_bd_encrypted);
        String bdName = getApplicationContext().getString(R.string.bd_name);
        String bdPassword = getApplicationContext().getString(R.string.bd_password);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, bdName);
        Database db = isBDEncrypted ? helper.getEncryptedWritableDb(bdPassword) : helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    @Override
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
