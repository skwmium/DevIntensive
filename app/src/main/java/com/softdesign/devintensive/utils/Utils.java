package com.softdesign.devintensive.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.softdesign.devintensive.common.App;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by skwmium on 08.07.16.
 */
public class Utils {
    @Nullable
    static Point sFullScreenWidthRatio16;

    // ---------- INTENTS ----------
    public static void dialPhoneNumber(@NonNull Context context, @Nullable String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) return;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(context, intent);
    }

    public static void sendEmail(@NonNull Context context, @Nullable String subject, @Nullable String mailto) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        if (mailto != null) intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mailto});
        if (subject != null) intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        startActivity(context, intent);
    }

    public static void openWebPage(@NonNull Context context, @Nullable String url) {
        if (url == null || url.isEmpty()) return;
        Uri webUrl = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webUrl);
        startActivity(context, intent);
    }

    public static void showPhotoPickerDialog(@NonNull Activity activity, int resultCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        Intent chooserIntent = Intent.createChooser(intent, null);
        activity.startActivityForResult(chooserIntent, resultCode);
    }

    @Nullable
    public static File takePhoto(@NonNull Activity activity) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(imageFileName, ".jpg", storageDir);
            if (image == null) return null;
            Uri photoURI = FileProvider.getUriForFile(activity, "com.softdesign.devintensive.fileprovider", image);

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            activity.startActivityForResult(takePictureIntent, Const.REQUEST_PHOTO_CAMERA);
            return image;
        } catch (Exception e) {
            L.e(e);
            return null;
        }
    }

    private static void startActivity(@NonNull Context context, @NonNull Intent intent) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }


    // ---------- PERMISSIONS ----------
    public static boolean checkPermissionAndRequestIfNotGranted(@NonNull Activity activity, @NonNull String permission, int requestCode) {
        return checkPermissionAndRequestIfNotGranted(activity, new String[]{permission}, requestCode);
    }

    public static boolean checkPermissionAndRequestIfNotGranted(@NonNull Activity activity, @NonNull String[] permissions, int requestCode) {
        boolean allGranted = true;
        for (String permission : permissions) {
            int selfPermission = ContextCompat.checkSelfPermission(activity, permission);
            if (selfPermission != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }
        if (!allGranted) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
            return false;
        }
        return true;
    }


    // ---------- COMMON ----------
    public static boolean isNullOrEmpty(@Nullable CharSequence charSequence) {
        return charSequence == null || charSequence.length() <= 0;
    }

    public static int getStatusBarHeight() {
        Resources resources = App.getInst().getResources();
        int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
        return identifier > 0 ? resources.getDimensionPixelSize(identifier) : 0;
    }

    public static int getActionBarHeight() {
        TypedValue typedValue = new TypedValue();
        if (App.getInst().getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            DisplayMetrics displayMetrics = App.getInst().getResources().getDisplayMetrics();
            return TypedValue.complexToDimensionPixelSize(typedValue.data, displayMetrics);
        }
        return 0;
    }

    public static Point getFullScreenWidthRatio16() {
        if (sFullScreenWidthRatio16 == null) {
            WindowManager wm = (WindowManager) App.getInst().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            sFullScreenWidthRatio16 = new Point(size.x, (int) (size.x / 1.78));
        }
        return sFullScreenWidthRatio16;
    }

    public static int lerp(int start, int end, float friction) {
        return (int) (start + (end - start) * friction);
    }

    public static float currentFriction(int start, int end, int current) {
        return (float) (current - start) / (end - start);
    }
}
