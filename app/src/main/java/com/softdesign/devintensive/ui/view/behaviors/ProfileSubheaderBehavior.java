package com.softdesign.devintensive.ui.view.behaviors;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by skwmium on 29.06.16.
 */
public class ProfileSubheaderBehavior extends CoordinatorLayout.Behavior<LinearLayout> {
    public ProfileSubheaderBehavior() {
    }

    public ProfileSubheaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, LinearLayout linearLayout, View view) {
        linearLayout.setY(view.getY());
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, LinearLayout linearLayout, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, LinearLayout linearLayout, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) linearLayout.getLayoutParams();
        float newHeight = linearLayout.getHeight() - dyConsumed;
        if (newHeight < linearLayout.getMinimumHeight()) {
            newHeight = linearLayout.getMinimumHeight();
        } else if (newHeight > (linearLayout.getMinimumHeight() * 2)) {
            newHeight = linearLayout.getMinimumHeight() * 2;
        }
        layoutParams.height = (int) newHeight;
        linearLayout.setLayoutParams(layoutParams);
    }
}
