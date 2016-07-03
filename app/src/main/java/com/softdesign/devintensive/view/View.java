package com.softdesign.devintensive.view;

import android.content.Context;
import android.support.annotation.StringRes;

/**
 * Created by skwmium on 01.07.16.
 */
public interface View {
    void showMessage(@StringRes int res);

    void showProgress();

    void hideProgress();

    Context getContext();
}
