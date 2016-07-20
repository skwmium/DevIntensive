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
import java.util.Collections;
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
    @Nullable
    OnItemChangedListener mOnItemChangedListener;

    public void setItemCLickListener(@Nullable OnItemCLickListener itemCLickListener) {
        mItemCLickListener = itemCLickListener;
    }

    public void setOnItemChangedListener(@Nullable OnItemChangedListener onItemChangedListener) {
        mOnItemChangedListener = onItemChangedListener;
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
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mProfileViewModels, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mProfileViewModels, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        if (mOnItemChangedListener != null) {
            mOnItemChangedListener.onItemMove(fromPosition, toPosition);
        }
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mProfileViewModels.remove(position);
        notifyItemRemoved(position);
        if (mOnItemChangedListener != null) {
            mOnItemChangedListener.onItemDismiss(position);
        }
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
