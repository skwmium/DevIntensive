package com.softdesign.devintensive.common;

import com.softdesign.devintensive.BuildConfig;

/**
 * Created by skwmium on 23.06.16.
 */
public final class BuildConfiguration {
    public static final boolean DEBUG = BuildConfig.DEBUG;

    // ---------- LOG ----------
    public static final boolean LOG_ENABLED = DEBUG;

    // ---------- ANALYTICS ----------
    public static final boolean CRASHLYTICS_ENABLED = true;//!DEBUG;

}
