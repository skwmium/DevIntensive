package com.softdesign.devintensive.di;

import com.softdesign.devintensive.ui.fragments.FragmentRestorePassword;

import dagger.Component;

/**
 * Created by skwmium on 08.07.16.
 */
@Component(modules = {ModuleRestorePassword.class})
public interface ComponentRestorePassword {
    void inject(FragmentRestorePassword fragmentRestorePassword);
}
