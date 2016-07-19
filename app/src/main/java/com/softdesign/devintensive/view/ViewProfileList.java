package com.softdesign.devintensive.view;

import android.os.Bundle;

import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;

import java.util.List;

/**
 * Created by skwmium on 14.07.16.
 */
public interface ViewProfileList extends View {
    void showProfileList(List<ProfileViewModel> profileViewModels);

    void startProfileView(Bundle arg);
}
