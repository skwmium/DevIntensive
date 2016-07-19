package com.softdesign.devintensive.view;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;

/**
 * Created by skwmium on 20.07.16.
 */
public interface ViewMain extends View {
    void setLocalUserProfileViewModel(ProfileViewModel profileViewModel);

    void showTakePhotoChooser();

    boolean checkPermissionsAndRequestIfNotGranted(@NonNull String[] permissons, int requestCode);

    void showPhotoPicker(int requestCode);

    void takePhoto(Uri photoFileUri);
}
