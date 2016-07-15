package com.softdesign.devintensive.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
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
public class AdapterProfileList extends RecyclerView.Adapter<AdapterProfileList.ViewHolder> {
    @NonNull
    private List<ProfileViewModel> mProfileViewModels = new ArrayList<>();

    public void setItems(List<ProfileViewModel> profileViewModels) {
        if (profileViewModels == null) {
            mProfileViewModels.clear();
        } else {
            mProfileViewModels = profileViewModels;
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemListProfileBinding profileBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_list_profile,
                parent, false);
        return new ViewHolder(profileBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getProfileBinding().setProfile(mProfileViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mProfileViewModels.size();
    }

    // ---------- HOLDER ----------
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemListProfileBinding mProfileBinding;

        public ViewHolder(ItemListProfileBinding profileBinding) {
            super(profileBinding.getRoot());
            mProfileBinding = profileBinding;
        }

        public ItemListProfileBinding getProfileBinding() {
            return mProfileBinding;
        }
    }
}
