package com.softdesign.devintensive.common;

import com.softdesign.devintensive.BuildConfig;

import okhttp3.logging.HttpLoggingInterceptor.Level;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by skwmium on 23.06.16.
 */
public final class BuildConfiguration {
    public static final boolean DEBUG = BuildConfig.DEBUG;

    // ---------- LOG ----------
    public static final boolean LOG_ENABLED = DEBUG;
    public static final Level HTTP_LOG_LEVEL = DEBUG ? BODY : NONE;

    // ---------- ANALYTICS ----------
    public static final boolean CRASHLYTICS_ENABLED = true;//!DEBUG;

}
