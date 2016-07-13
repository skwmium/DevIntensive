package com.softdesign.devintensive.ui.view.behaviors;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.utils.Utils;

/**
 * Created by skwmium on 10.07.16.
 */
public class CustomNestedScrollBehavior extends AppBarLayout.ScrollingViewBehavior {
    private final int mMaxAppBarHeight;
    private final int mMinAppBarHeight;
    private final int mMaxUserInfoHeight;

    public CustomNestedScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMinAppBarHeight = Utils.getStatusBarHeight() + Utils.getActionBarHeight();
        mMaxAppBarHeight = context.getResources().getDimensionPixelSize(R.dimen.profile_image_height);
        mMaxUserInfoHeight = context.getResources().getDimensionPixelSize(R.dimen.profile_subheader_height);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float currentFriction = Utils.currentFriction(mMinAppBarHeight, mMaxAppBarHeight, dependency.getBottom());
        int transY = Utils.lerp(mMaxUserInfoHeight / 2, mMaxUserInfoHeight, currentFriction);

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.topMargin = transY;
        child.setLayoutParams(lp);

        return super.onDependentViewChanged(parent, child, dependency);
    }
}
