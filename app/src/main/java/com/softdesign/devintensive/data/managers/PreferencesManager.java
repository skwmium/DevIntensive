package com.softdesign.devintensive.data.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.softdesign.devintensive.common.App;

/**
 * Created by skwmium on 28.06.16.
 */
@SuppressWarnings("unused")
public class PreferencesManager {
    private static final PreferencesManager sInstance = new PreferencesManager(App.getInst());

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mPrefFileName;


    public static PreferencesManager getInst() {
        return sInstance;
    }

    @SuppressLint("CommitPrefEdits")
    private PreferencesManager(Context context) {
        mPrefFileName = context.getPackageName().replace('.', '_');
        mSharedPreferences = context.getSharedPreferences(mPrefFileName, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    private SharedPreferences preference(String key) {
        return mSharedPreferences;
    }

    public String getPrefFileName() {
        return mPrefFileName;
    }

    public boolean get(String key, boolean defValue) {
        return preference(key).getBoolean(key, defValue);
    }

    public float get(String key, float defValue) {
        return preference(key).getFloat(key, defValue);
    }

    public int get(String key, int defValue) {
        return preference(key).getInt(key, defValue);
    }

    public long get(String key, long defValue) {
        return preference(key).getLong(key, defValue);
    }

    public String get(String key, String defValue) {
        return preference(key).getString(key, defValue);
    }

    public void put(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public void put(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    public void put(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public void put(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public void put(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }
}
