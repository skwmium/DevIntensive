package com.softdesign.devintensive.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;

import java.io.File;

/**
 * Created by skwmium on 05.07.16.
 */
public interface ViewProfile extends View {
    @Nullable
    Bundle getArguments();

    void setProfileViewModel(ProfileViewModel profileViewModel);

    void showTakePhotoChooser();

    void setEditMode(boolean editMode);

    boolean checkPermissionsAndRequestIfNotGranted(@NonNull String[] permissons, int requestCode);

    void showPhotoPicker(int requestCode);

    void takePhoto(File file, int requestCode);
}
