package com.example.minitumblr.controller.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

import androidx.annotation.Nullable;

public class SharedPrefs {

    private final String TAG = SharedPrefs.class.getName();

    private final Context context;
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SharedPrefs(Context context) {
        this.context = context;
        this.prefs = this.context.getSharedPreferences(this.context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
        this.editor = this.edit();
    }

    public SharedPrefs(Context context, int mode) {
        this.context = context;
        this.prefs = this.context.getSharedPreferences(this.context.getApplicationContext().getPackageName(), mode);
        this.editor = this.edit();
    }

    public Map<String, ?> getAll() {
        return this.prefs.getAll();
    }

    public String get(Constants.SharedKey key, @Nullable String defValue) {
        return this.prefs.getString(key.toString(), defValue);
    }

    public Set<String> get(Constants.SharedKey key, @Nullable Set<String> defValues) {
        return this.prefs.getStringSet(key.toString(), defValues);
    }

    public int get(Constants.SharedKey key, int defValue) {
        return this.prefs.getInt(key.toString(), defValue);
    }

    public long get(Constants.SharedKey key, long defValue) {
        return this.prefs.getLong(key.toString(), defValue);
    }

    public float get(Constants.SharedKey key, float defValue) {
        return this.prefs.getFloat(key.toString(), defValue);
    }

    public boolean get(Constants.SharedKey key, boolean defValue) {
        return this.prefs.getBoolean(key.toString(), defValue);
    }

    public void set(Constants.SharedKey key, String value) {
        this.editor.putString(key.toString(), value).apply();
    }

    public void set(Constants.SharedKey key, Long value) {
        this.editor.putLong(key.toString(), value).apply();
    }

    public void set(Constants.SharedKey key, boolean value) {
        this.editor.putBoolean(key.toString(), value).apply();
    }

    public void remove(Constants.SharedKey key) {
        this.editor.remove(key.toString()).apply();
    }

    private SharedPreferences.Editor edit() {
        return this.prefs.edit();
    }

}
