package com.softdesign.devintensive.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by skwmium on 08.07.16.
 */
public class Utils {

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

    private static void startActivity(@NonNull Context context, @NonNull Intent intent) {
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
