package com.softdesign.devintensive.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.databinding.ActivityProfileBinding;
import com.softdesign.devintensive.utils.L;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityProfileBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        binding.setProfile(ProfileViewModel.createTestProfile());

        L.d("onCreate", savedInstanceState == null ?
                " (is first start)" : " (isn't first start)");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L.d("onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        L.d("onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.d("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.d("onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        L.d("onPause");
    }
}
