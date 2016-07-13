package com.softdesign.devintensive.ui.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.softdesign.devintensive.utils.TypefaceHelper;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by skwmium on 26.06.16.
 */
public class InflaterFactory implements LayoutInflater.Factory {
    private final LayoutInflater.Factory mBaseFactory;

    public InflaterFactory() {
        mBaseFactory = null;
    }

    public InflaterFactory(LayoutInflater.Factory baseFactory) {
        mBaseFactory = baseFactory;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attributeSet) {
        if (name.equalsIgnoreCase(ImageView.class.getSimpleName())) {
            return overrideImageViewIfNeed(context, attributeSet);
        } else if (name.equalsIgnoreCase(TextView.class.getSimpleName())) {
            return overrideTextViewIfNeed(context, attributeSet);
        }
        if (mBaseFactory != null)
            return mBaseFactory.onCreateView(name, context, attributeSet);
        return null;
    }

    private View overrideImageViewIfNeed(Context context, AttributeSet attributeSet) {
        boolean isCircular = attributeSet.getAttributeBooleanValue(
                "http://schemas.android.com/apk/res-auto", "circular", false);
        if (isCircular) return new CircleImageView(context, attributeSet);
        return null;
    }

    private View overrideTextViewIfNeed(Context context, AttributeSet attributeSet) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return null;
        }
        TextView textView = new TextView(context, attributeSet);
        TypefaceHelper.applyFontToTextView(context, textView, attributeSet);
        return textView;
    }
}
