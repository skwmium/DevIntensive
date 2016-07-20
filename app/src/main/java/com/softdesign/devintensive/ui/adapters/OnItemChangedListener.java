package com.softdesign.devintensive.ui.adapters;

/**
 * Created by skwmium on 20.07.16.
 */
public interface OnItemChangedListener {
    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
