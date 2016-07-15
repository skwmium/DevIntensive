package com.softdesign.devintensive.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.softdesign.devintensive.R;

/**
 * Created by skwmium on 14.07.16.
 */
public class AspectRatioImageView extends ImageView {
    private static final float ASPECT_RATIO_16_9 = 1.78f;
    private static final float ASPECT_RATIO_4_3 = 1.33f;
    private static final float ASPECT_RATIO_1_1 = 1f;

    private float mAspectRatio;

    public AspectRatioImageView(Context context) {
        super(context);
        init(context, null);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int flag = 0;
        if (context != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView);
            flag = a.getInt(R.styleable.AspectRatioImageView_aspect_ratio, 0);
            a.recycle();
        }
        mAspectRatio = flagToFloat(flag);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int newWidth = getMeasuredWidth();
        int newHeight = (int) (newWidth / mAspectRatio);
        setMeasuredDimension(newWidth, newHeight);
    }


    //magic numbers! Check 'aspect_ratio' attribute!
    private float flagToFloat(int flag) {
        switch (flag) {
            case 0:
                return ASPECT_RATIO_16_9;
            case 1:
                return ASPECT_RATIO_4_3;
            case 2:
                return ASPECT_RATIO_1_1;
            default:
                return ASPECT_RATIO_16_9;
        }
    }
}
