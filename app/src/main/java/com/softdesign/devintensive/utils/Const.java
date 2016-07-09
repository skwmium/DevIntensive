package com.softdesign.devintensive.utils;

/**
 * Created by skwmium on 28.06.16.
 */
public final class Const {

    // ---------- PREFS ----------
    public static final String PREF_AUTH_TOKEN = "_pref_auth_token";
    public static final String PREF_USER_ID = "_pref_user_id";
    public static final String PREF_PROFILE = "_pref_profile";


    // ---------- KEYS ----------
    public static final String KEY_PROFILE = "_key_profile";


    // ---------- REQUESTS ----------
    public static final int REQUEST_PHOTO_PICKER = 1001;
    public static final int REQUEST_PHOTO_CAMERA = 1002;
    public static final int REQUEST_PERMISSION_CAMERA = 1003;
    public static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 1004;


    // ---------- OTHER ----------
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final String UI_THREAD = "UI_THREAD";
    public static final String IO_THREAD = "IO_THREAD";

    private Const() {
    }
}
