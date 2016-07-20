package com.softdesign.devintensive.utils;

import okhttp3.MediaType;

/**
 * Created by skwmium on 28.06.16.
 */
public final class Const {

    // ---------- PREFS ----------
    public static final String PREF_AUTH_TOKEN = "_pref_auth_token";
    public static final String PREF_USER_ID = "_pref_user_id";
    public static final String PREF_PROFILE = "_pref_profile";
    public static final String PREF_USER = "_pref_user";


    // ---------- KEYS ----------
    public static final String KEY_PROFILE = "_key_profile";
    public static final String KEY_PROFILE_LIST = "_key_profile_list";


    // ---------- REQUESTS ----------
    public static final int REQUEST_MAIN_PHOTO_PICKER = 1001;
    public static final int REQUEST_MAIN_PHOTO_CAMERA = 1002;
    public static final int REQUEST_MAIN_PERMISSION_CAMERA = 1003;
    public static final int REQUEST_MAIN_PERMISSION_READ_EXTERNAL_STORAGE = 1004;
    public static final int REQUEST_PROFILE_PHOTO_PICKER = 1001;
    public static final int REQUEST_PROFILE_PHOTO_CAMERA = 1002;
    public static final int REQUEST_PROFILE_PERMISSION_CAMERA = 1003;
    public static final int REQUEST_PROFILE_PERMISSION_READ_EXTERNAL_STORAGE = 1004;


    // ---------- STORAGE ----------
    public static final String DATABASE_NAME = "devintensive-db";


    // ---------- OTHER ----------
    public static final long SPLASH_SCREEN_DELAY = 500L;
    public static final String FILE_PROVIDER_AUTHORITY = "com.softdesign.devintensive.fileprovider";
    public static final MediaType MEDIA_TYPE_MULTIPART_FORM_DATA = MediaType.parse("multipart/form-data");
    public static final String UI_THREAD = "UI_THREAD";
    public static final String IO_THREAD = "IO_THREAD";
    public static final String PHOTO_FILE_PREFIX = "IMG_";


    private Const() {
    }
}
