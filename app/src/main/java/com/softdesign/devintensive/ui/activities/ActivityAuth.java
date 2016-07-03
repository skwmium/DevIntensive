package com.softdesign.devintensive.ui.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.presenter.BasePresenter;
import com.softdesign.devintensive.ui.fragments.BaseFragment;
import com.softdesign.devintensive.ui.fragments.FragmentAuth;

/**
 * Created by skwmium on 28.06.16.
 */
public class ActivityAuth extends BaseActivity {
    private static String TAG = "TAG";

    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context, ActivityAuth.class);
        context.startActivity(intent);
    }

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        mFragmentManager = getFragmentManager();

        Fragment fragment = mFragmentManager.findFragmentByTag(TAG);
        if (fragment == null) replaceFragment(new FragmentAuth(), false);
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    private void replaceFragment(BaseFragment fragment, boolean addBackStack) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment, TAG);
        if (addBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }
}
