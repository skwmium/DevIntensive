package com.softdesign.devintensive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.di.DaggerComponentView;
import com.softdesign.devintensive.di.ModuleViewDynamically;
import com.softdesign.devintensive.presenter.BasePresenter;
import com.softdesign.devintensive.presenter.PresenterProfileList;
import com.softdesign.devintensive.ui.activities.BaseActivity;
import com.softdesign.devintensive.ui.adapters.AdapterProfileList;
import com.softdesign.devintensive.ui.adapters.OnItemChangedListener;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;
import com.softdesign.devintensive.view.ViewProfileList;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by skwmium on 19.07.16.
 */
public class FragmentProfileList extends BaseFragment implements ViewProfileList,
        SearchView.OnQueryTextListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Inject
    PresenterProfileList presenter;
    private AdapterProfileList mAdapterProfileList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerComponentView
                .builder()
                .moduleViewDynamically(new ModuleViewDynamically(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        presenter.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    private void init() {
        activityCallback.setActionBar(toolbar);
        toolbar.setOnClickListener(view -> recyclerView.scrollToPosition(0));
        initRecycler();

        BaseActivity baseActivity = getBaseActivity();
        ActionBar actionBar = null;
        if (baseActivity != null) {
            actionBar = baseActivity.getSupportActionBar();
        }
        if (actionBar != null) {
            actionBar.setTitle(R.string.nav_item_contacts);
        }
    }

    private void initRecycler() {
        mAdapterProfileList = new AdapterProfileList();
        mAdapterProfileList.setItemCLickListener(presenter);
        mAdapterProfileList.setOnItemChangedListener(presenter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapterProfileList);

        Callback callback = new MyTouchHelperCallback(mAdapterProfileList);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_profile_list, menu);

        MenuItem searchItem = menu.findItem(R.id.nav_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void showProfileList(List<ProfileViewModel> profileViewModels) {
        mAdapterProfileList.setItems(profileViewModels);
    }

    @Override
    public void startProfileView(Bundle arg) {
        activityCallback.startProfileFragment(arg);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        presenter.filterProfiles(newText);
        return false;
    }


    // ---------- TOUCH HELPER CALLBACK----------
    class MyTouchHelperCallback extends Callback {
        private final OnItemChangedListener mAdapter;

        public MyTouchHelperCallback(OnItemChangedListener adapter) {
            mAdapter = adapter;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = android.support.v7.widget.helper.ItemTouchHelper.UP | android.support.v7.widget.helper.ItemTouchHelper.DOWN;
            int swipeFlags = android.support.v7.widget.helper.ItemTouchHelper.START | android.support.v7.widget.helper.ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }
    }
}
