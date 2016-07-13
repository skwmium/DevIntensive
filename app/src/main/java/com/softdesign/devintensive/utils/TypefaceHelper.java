package com.softdesign.devintensive.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skwmium on 10.07.16.
 */
public class TypefaceHelper {
    private static final String FONT_FILE_POSTFIX = ".ttf";

    private static final String PATH_REGULAR = "fonts/regular/";
    private static final String PATH_BOLD = "fonts/bold/";
    private static final String PATH_ITALIC = "fonts/italic/";
    private static final String PATH_BOLD_ITALIC = "fonts/bolditalic/";

    private static final Map<String, Typeface> FONTS = new HashMap<>();

    public static void applyFontToTextView(Context context, TextView textView, AttributeSet attrs) {
        int[] fontStyleAttributes = {android.R.attr.textStyle, android.R.attr.fontFamily};
        TypedArray typedArray = context.obtainStyledAttributes(attrs, fontStyleAttributes);

        int textStyle = typedArray.getInt(0, Typeface.NORMAL);
        @SuppressWarnings("ResourceType")
        String fontFamily = typedArray.getString(1);
        typedArray.recycle();

        Typeface typeface = getTypeface(context, textStyle, fontFamily);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
    }

    @Nullable
    private static Typeface getTypeface(@NonNull Context context, @IntegerRes int textStyle, @Nullable String fontFamily) {
        String folder = getPathByStyle(textStyle);
        if (fontFamily == null || folder == null) return null;

        String fullPath = folder + fontFamily + FONT_FILE_POSTFIX;
        if (FONTS.containsKey(fullPath))
            return FONTS.get(fullPath);

        Typeface typeface = getTypefaceFromAssets(context, fullPath);
        if (typeface != null) {
            FONTS.put(fullPath, typeface);
            return typeface;
        }
        return null;
    }

    @Nullable
    private static Typeface getTypefaceFromAssets(@NonNull Context context, @Nullable String path) {
        try {
            return Typeface.createFromAsset(context.getAssets(), path);
        } catch (Exception ignored) {
            return null;
        }
    }

    @Nullable
    private static String getPathByStyle(@IntegerRes int textStyle) {
        switch (textStyle) {
            case Typeface.NORMAL:
                return PATH_REGULAR;
            case Typeface.BOLD:
                return PATH_BOLD;
            case Typeface.ITALIC:
                return PATH_ITALIC;
            case Typeface.BOLD_ITALIC:
                return PATH_BOLD_ITALIC;
            default:
                return null;
        }
    }
}