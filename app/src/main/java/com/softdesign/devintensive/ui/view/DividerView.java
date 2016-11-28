package com.softdesign.devintensive.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.softdesign.devintensive.R;

/**
 * Created by skwmium on 15.07.16.
 */
public class DividerView extends View {
    public DividerView(Context context) {
        super(context);
        init(context, null);
    }

    public DividerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int resourceId = android.R.color.transparent;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DividerView);
            resourceId = typedArray.getResourceId(R.styleable.DividerView_dividerColor, resourceId);
            typedArray.recycle();
        }
        setBackgroundResource(resourceId);
    }
}
