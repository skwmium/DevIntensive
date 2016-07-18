package com.softdesign.devintensive.ui.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.softdesign.devintensive.databinding.ProfileSubheaderBinding;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;

/**
 * Created by skwmium on 14.07.16.
 */
public class ProfilePublicInfoView extends FrameLayout {

    public ProfilePublicInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ProfileSubheaderBinding.inflate(inflater, this, true);
    }

    @BindingAdapter({"bind:profile"})
    public static void setVariable(ProfilePublicInfoView view, ProfileViewModel model) {
        if (view.getChildCount() == 0) {
            return;
        }
        View boundView = view.getChildAt(0);
        ProfileSubheaderBinding binding = DataBindingUtil.getBinding(boundView);
        binding.setProfile(model);
    }
}
