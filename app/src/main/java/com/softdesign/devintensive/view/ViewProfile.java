package com.softdesign.devintensive.view;

import android.content.Intent;

import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;

/**
 * Created by skwmium on 05.07.16.
 */
public interface ViewProfile extends View {
    Intent getIntent();

    void setProfileViewModel(ProfileViewModel profileViewModel);
}
