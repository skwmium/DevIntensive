package com.softdesign.devintensive.ui.viewmodel;

import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.softdesign.devintensive.BR;

/**
 * Created by skwmium on 21.07.16.
 */
public class RepositoryViewModel extends BaseViewModel implements EditableModel {
    private String mRepository;
    private boolean mIsEditable;
    private boolean mIsCanBeEditable;

    public RepositoryViewModel() {
    }

    protected RepositoryViewModel(Parcel in) {
        mRepository = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mRepository);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RepositoryViewModel> CREATOR = new Parcelable.Creator<RepositoryViewModel>() {
        @Override
        public RepositoryViewModel createFromParcel(Parcel in) {
            return new RepositoryViewModel(in);
        }

        @Override
        public RepositoryViewModel[] newArray(int size) {
            return new RepositoryViewModel[size];
        }
    };

    @Override
    @Bindable
    public boolean isEditable() {
        return mIsEditable;
    }

    @Override
    @Bindable
    public boolean isCanBeEditable() {
        return mIsCanBeEditable;
    }

    @Bindable
    public String getRepository() {
        return mRepository;
    }


    // ---------- SETTERS ----------
    public void setRepository(String repository) {
        mRepository = repository;
        notifyPropertyChanged(BR.repository);
    }

    public void setEditable(boolean editable) {
        mIsEditable = editable;
        notifyPropertyChanged(BR.editable);
    }

    public void setCanBeEditable(boolean canBeEditable) {
        mIsCanBeEditable = canBeEditable;
        notifyPropertyChanged(BR.canBeEditable);
    }
}
