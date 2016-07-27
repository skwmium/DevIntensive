package com.softdesign.devintensive.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.databinding.ItemListProfileBinding;
import com.softdesign.devintensive.ui.viewmodel.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skwmium on 14.07.16.
 */
public class AdapterProfileList extends RecyclerView.Adapter<AdapterProfileList.ViewHolder>
        implements OnItemChangedListener {
    @NonNull
    private List<ProfileViewModel> mProfileViewModels = new ArrayList<>();
    @Nullable
    private OnItemCLickListener mItemCLickListener;

    public void setItemCLickListener(@Nullable OnItemCLickListener itemCLickListener) {
        mItemCLickListener = itemCLickListener;
    }

    public void setItems(List<ProfileViewModel> profileViewModels) {
        if (profileViewModels == null) {
            mProfileViewModels.clear();
        } else {
            mProfileViewModels = profileViewModels;
        }
        notifyDataSetChanged();
    }

    private ProfileViewModel getItem(int position) {
        return mProfileViewModels.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListProfileBinding profileBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_list_profile,
                parent, false);
        return new ViewHolder(profileBinding, mItemCLickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getProfileBinding().setProfile(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mProfileViewModels.size();
    }


    // ---------- ITEM CHANGE ----------
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        notifyItemRemoved(position);
    }


    // ---------- HOLDER ----------
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemListProfileBinding mProfileBinding;

        public ViewHolder(ItemListProfileBinding profileBinding, OnItemCLickListener listener) {
            super(profileBinding.getRoot());
            mProfileBinding = profileBinding;
            mProfileBinding.buttonOpen.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onItemClick(mProfileBinding.getProfile());
                }
            });
        }

        public ItemListProfileBinding getProfileBinding() {
            return mProfileBinding;
        }
    }
}
