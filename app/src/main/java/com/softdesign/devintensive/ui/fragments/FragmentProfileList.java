package com.softdesign.devintensive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.di.DaggerComponentProfileList;
import com.softdesign.devintensive.di.ModuleProfileList;
import com.softdesign.devintensive.presenter.BasePresenter;
import com.softdesign.devintensive.presenter.PresenterProfileList;
import com.softdesign.devintensive.ui.adapters.AdapterProfileList;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.view.ViewProfileList;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by skwmium on 14.07.16.
 */
public class FragmentProfileList extends BaseFragment implements ViewProfileList {
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Inject
    PresenterProfileList mPresenter;
    private AdapterProfileList mAdapterProfileList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerComponentProfileList
                .builder()
                .moduleProfileList(new ModuleProfileList(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapterProfileList = new AdapterProfileList();
        recyclerView.setAdapter(mAdapterProfileList);

        mPresenter.onViewCreated(savedInstanceState);
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
}
