package com.softdesign.devintensive.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.databinding.ItemListRepositoryBinding;
import com.softdesign.devintensive.ui.viewmodel.RepositoryViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skwmium on 21.07.16.
 */
public class AdapterProfileRepositories extends RecyclerView.Adapter<AdapterProfileRepositories.ViewHolder> {
    private List<RepositoryViewModel> mRepositoryViewModels = new ArrayList<>();
    @Nullable
    private OnItemCLickListener mItemCLickListener;

    public void setItemCLickListener(@Nullable OnItemCLickListener itemCLickListener) {
        mItemCLickListener = itemCLickListener;
    }

    public AdapterProfileRepositories(List<RepositoryViewModel> repositoryViewModels) {
        if (repositoryViewModels != null)
            mRepositoryViewModels = repositoryViewModels;
    }

    private RepositoryViewModel getItem(int position) {
        return mRepositoryViewModels.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListRepositoryBinding repositoryBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_list_repository,
                parent, false);
        return new ViewHolder(repositoryBinding, mItemCLickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getRepositoryBinding().setRepo(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mRepositoryViewModels.size();
    }


    // ---------- HOLDER ----------
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemListRepositoryBinding mRepositoryBinding;

        public ViewHolder(ItemListRepositoryBinding repositoryBinding, OnItemCLickListener listener) {
            super(repositoryBinding.getRoot());
            mRepositoryBinding = repositoryBinding;
            mRepositoryBinding.imageActionRepo.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onItemClick(mRepositoryBinding.getRepo());
                }
            });
        }

        public ItemListRepositoryBinding getRepositoryBinding() {
            return mRepositoryBinding;
        }
    }
}
