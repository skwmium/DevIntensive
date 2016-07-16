package com.softdesign.devintensive.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.di.DaggerComponentProfileList;
import com.softdesign.devintensive.di.ModuleProfileList;
import com.softdesign.devintensive.presenter.BasePresenter;
import com.softdesign.devintensive.presenter.PresenterProfileList;
import com.softdesign.devintensive.ui.adapters.AdapterProfileList;
import com.softdesign.devintensive.ui.adapters.OnItemCLickListener;
import com.softdesign.devintensive.ui.viewmodel.BaseViewModel;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.view.ViewProfileList;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by skwmium on 15.07.16.
 */
public class ActivityProfileList extends BaseActivity implements ViewProfileList, OnItemCLickListener {
    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context, ActivityProfileList.class);
        context.startActivity(intent);
    }

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Inject
    PresenterProfileList mPresenter;
    private AdapterProfileList mAdapterProfileList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);

        DaggerComponentProfileList
                .builder()
                .moduleProfileList(new ModuleProfileList(this))
                .build()
                .inject(this);

        init();
        mPresenter.onCreate(savedInstanceState);
    }

    private void init() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.nav_item_contacts);
        }
        mAdapterProfileList = new AdapterProfileList();
        mAdapterProfileList.setItemCLickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapterProfileList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void showProfileList(List<ProfileViewModel> profileViewModels) {
        mAdapterProfileList.setItems(profileViewModels);
    }

    @Override
    public void onItemClick(BaseViewModel viewModel) {
        ProfileViewModel profileViewModel = (ProfileViewModel) viewModel;
        ActivityProfile.start(this, profileViewModel);
    }
}
