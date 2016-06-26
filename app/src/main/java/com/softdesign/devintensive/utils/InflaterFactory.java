package com.softdesign.devintensive.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

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
            boolean isCircular = attributeSet.getAttributeBooleanValue(
                    "http://schemas.android.com/apk/res-auto", "circular", false);
            if (isCircular) {
                return new CircleImageView(context, attributeSet);
            }
        }
        return null;
    }
}
