package com.softdesign.devintensive.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.storage.LocalUser;
import com.softdesign.devintensive.databinding.NavHeaderMainBinding;
import com.softdesign.devintensive.di.DaggerComponentView;
import com.softdesign.devintensive.di.ModuleViewDynamically;
import com.softdesign.devintensive.presenter.PresenterMain;
import com.softdesign.devintensive.ui.dialogs.DialogChooseProfilePhoto;
import com.softdesign.devintensive.ui.fragments.FragmentAuth;
import com.softdesign.devintensive.ui.fragments.FragmentProfile;
import com.softdesign.devintensive.ui.fragments.FragmentProfileList;
import com.softdesign.devintensive.ui.fragments.FragmentRestorePassword;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.utils.L;
import com.softdesign.devintensive.utils.LogoutEvent;
import com.softdesign.devintensive.utils.Utils;
import com.softdesign.devintensive.view.ViewMain;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;

import static com.softdesign.devintensive.ui.dialogs.DialogChooseProfilePhoto.OnChooseItemListener.Type.CAMERA;
import static com.softdesign.devintensive.ui.dialogs.DialogChooseProfilePhoto.OnChooseItemListener.Type.GALLERY;

/**
 * Created by skwmium on 19.07.16.
 */
public class ActivityMain extends BaseActivity implements ViewMain, ActivityMainCallback,
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String FRAGMENT_TAG = "_fr_tag";

    public static void start(Context context) {
        Intent intent = new Intent(context, ActivityMain.class);
        context.startActivity(intent);
    }

    @Inject
    PresenterMain presenter;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private NavHeaderMainBinding mNavHeaderMainBinding;
    private FragmentManager mFragmentManager;


    // ---------- ACTIVITY LOGIC ----------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerComponentView
                .builder()
                .moduleViewDynamically(new ModuleViewDynamically(this))
                .build()
                .inject(this);

        EventBus.getDefault().register(this);
        mFragmentManager = getSupportFragmentManager();
        init();
        openInitialFragment();
        presenter.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    // ---------- ACTIVITY CALLBACK ----------
    @Override
    public void setActionBar(Toolbar toolbar) {
        if (toolbar != null && drawerLayout != null) {
            setSupportActionBar(toolbar);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
            //noinspection deprecation
            drawerLayout.setDrawerListener(toggle);
            toggle.syncState();
        }
    }

    @Override
    public void setDrawerLocked(boolean locked) {
        if (drawerLayout != null) {
            drawerLayout.setDrawerLockMode(locked ?
                    DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public void startProfileFragment(Bundle arg) {
        FragmentProfile fragmentProfile = new FragmentProfile();
        fragmentProfile.setArguments(arg);
        replaceFragment(fragmentProfile, true);
    }

    @Override
    public void startProfileListFragment() {
        FragmentProfileList fragmentProfileList = new FragmentProfileList();
        replaceFragment(fragmentProfileList, true);
    }

    @Override
    public void startAuthFragment() {
        mFragmentManager.popBackStack();
        FragmentAuth fragmentAuth = new FragmentAuth();
        replaceFragment(fragmentAuth, false);
    }

    @Override
    public void startRestorePasswordFragment() {
        FragmentRestorePassword fragmentRestorePassword = new FragmentRestorePassword();
        replaceFragment(fragmentRestorePassword, true);
    }


    // ---------- MVP ----------
    @Override
    public void setLocalUserProfileViewModel(ProfileViewModel profileViewModel) {
        mNavHeaderMainBinding.setProfile(profileViewModel);
    }

    @Override
    public boolean checkPermissionsAndRequestIfNotGranted(@NonNull String[] permissons, int requestCode) {
        return Utils.checkPermissionsAndRequestIfNotGranted(this, permissons, requestCode);
    }

    @Override
    public void showPhotoPicker(int requestCode) {
        Utils.showPhotoPickerDialog(this, requestCode);
    }

    @Override
    public void takePhoto(Uri photoFileUri) {
        Utils.takePhoto(this, photoFileUri);
    }

    @Override
    public void showTakePhotoChooser() {
        new DialogChooseProfilePhoto()
                .setListener(type -> {
                    if (type == GALLERY)
                        presenter.openGalleryClicked();
                    else if (type == CAMERA)
                        presenter.takePhotoClicked();
                }).show(getSupportFragmentManager(), null);
    }


    // ---------- BUS EVENT ----------
    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutEvent(LogoutEvent event) {
        L.e("onLogOut event received");
        mFragmentManager.popBackStack();
        replaceFragment(new FragmentAuth(), false);
    }


    // ---------- CALLBACKS ----------
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (drawerLayout != null) drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.nav_item_profile:
                startProfileFragment(null);
                return true;
            case R.id.nav_item_contacts:
                startProfileListFragment();
                return true;
            case R.id.nav_item_logout:
                LocalUser.getInst().logout();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_avatar:
                presenter.changeProfileAvatarClicked();
                break;
        }
    }


    // ---------- UTIL ----------
    private void init() {
        mNavHeaderMainBinding = DataBindingUtil.bind(navigationView.getHeaderView(0));
        mNavHeaderMainBinding.imageAvatar.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void openInitialFragment() {
        Fragment fragment = mFragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment != null) return;

        if (!LocalUser.getInst().isLogined()) {
            startAuthFragment();
        } else {
            startProfileFragment(null);
        }
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment, FRAGMENT_TAG);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
