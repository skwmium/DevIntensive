package com.softdesign.devintensive.ui.viewmodel;

import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.softdesign.devintensive.BR;
import com.softdesign.devintensive.utils.Utils;

/**
 * Created by skwmium on 23.06.16.
 * <p>
 * Приведение данных к необходимому для отображения виду происходит в сеттерах,
 * тк Data Binding позволяет менять модель не из UI потока, и в этом случае преобразования
 * не будут тормозить ui
 */
public class ProfileViewModel extends BaseViewModel implements EditableModel {
    private String mName;
    private String mRating;
    private String mLinesCount;
    private String mProjectCount;
    private String mMobilePhoneNumber;
    private String mEmail;
    private String mVkProfile;
    private String mRepository;
    private String mAbout;
    private String mAvatarUrl;
    private String mPhotoUrl;
    private boolean mIsEditable;
    private boolean mIsCanBeEditable;

    public ProfileViewModel() {
    }

    protected ProfileViewModel(Parcel in) {
        mName = in.readString();
        mRating = in.readString();
        mLinesCount = in.readString();
        mProjectCount = in.readString();
        mMobilePhoneNumber = in.readString();
        mEmail = in.readString();
        mVkProfile = in.readString();
        mRepository = in.readString();
        mAbout = in.readString();
        mAvatarUrl = in.readString();
        mPhotoUrl = in.readString();
        mIsEditable = in.readByte() != 0x00;
        mIsCanBeEditable = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mRating);
        dest.writeString(mLinesCount);
        dest.writeString(mProjectCount);
        dest.writeString(mMobilePhoneNumber);
        dest.writeString(mEmail);
        dest.writeString(mVkProfile);
        dest.writeString(mRepository);
        dest.writeString(mAbout);
        dest.writeString(mAvatarUrl);
        dest.writeString(mPhotoUrl);
        dest.writeByte((byte) (mIsEditable ? 0x01 : 0x00));
        dest.writeByte((byte) (mIsCanBeEditable ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProfileViewModel> CREATOR = new Parcelable.Creator<ProfileViewModel>() {
        @Override
        public ProfileViewModel createFromParcel(Parcel in) {
            return new ProfileViewModel(in);
        }

        @Override
        public ProfileViewModel[] newArray(int size) {
            return new ProfileViewModel[size];
        }
    };

    @BindingAdapter({"bind:imageUrl", "bind:placeholder"})
    public static void loadImage(ImageView view, String url, Drawable placeholder) {
        if (Utils.isNullOrEmpty(url))
            return;
        Glide.with(view.getContext())
                .load(url)
                .placeholder(placeholder)
                .dontAnimate()
                .into(view);
    }

    public void updateValues(@Nullable ProfileViewModel profileViewModel) {
        if (profileViewModel == null) return;
        if (mName != null && !mName.equals(profileViewModel.mName)) {
            mName = profileViewModel.mName;
            notifyPropertyChanged(BR.name);
        }
        if (mRating != null && !mRating.equals(profileViewModel.mRating)) {
            mRating = profileViewModel.mRating;
            notifyPropertyChanged(BR.rating);
        }
        if (mLinesCount != null && !mLinesCount.equals(profileViewModel.mLinesCount)) {
            mLinesCount = profileViewModel.mLinesCount;
            notifyPropertyChanged(BR.linesCount);
        }
        if (mProjectCount != null && !mProjectCount.equals(profileViewModel.mProjectCount)) {
            mProjectCount = profileViewModel.mProjectCount;
            notifyPropertyChanged(BR.projectCount);
        }
        if (mMobilePhoneNumber != null && !mMobilePhoneNumber.equals(profileViewModel.mMobilePhoneNumber)) {
            mMobilePhoneNumber = profileViewModel.mMobilePhoneNumber;
            notifyPropertyChanged(BR.mobilePhoneNumber);
        }
        if (mEmail != null && !mEmail.equals(profileViewModel.mEmail)) {
            mEmail = profileViewModel.mEmail;
            notifyPropertyChanged(BR.email);
        }
        if (mVkProfile != null && !mVkProfile.equals(profileViewModel.mVkProfile)) {
            mVkProfile = profileViewModel.mVkProfile;
            notifyPropertyChanged(BR.vkProfile);
        }
        if (mRepository != null && !mRepository.equals(profileViewModel.mRepository)) {
            mRepository = profileViewModel.mRepository;
            notifyPropertyChanged(BR.repository);
        }
        if (mAbout != null && !mAbout.equals(profileViewModel.mAbout)) {
            mAbout = profileViewModel.mAbout;
            notifyPropertyChanged(BR.about);
        }
        if (mAvatarUrl != null && !mAvatarUrl.equals(profileViewModel.mAvatarUrl)) {
            mAvatarUrl = profileViewModel.mAvatarUrl;
            notifyPropertyChanged(BR.avatarUrl);
        }
        if (mPhotoUrl != null && !mPhotoUrl.equals(profileViewModel.mPhotoUrl)) {
            mPhotoUrl = profileViewModel.mPhotoUrl;
            notifyPropertyChanged(BR.photoUrl);
        }
    }

    @Bindable
    public String getName() {
        return mName;
    }

    @Bindable
    public String getRating() {
        return mRating;
    }

    @Bindable
    public String getLinesCount() {
        return mLinesCount;
    }

    @Bindable
    public String getProjectCount() {
        return mProjectCount;
    }

    @Bindable
    public String getMobilePhoneNumber() {
        return mMobilePhoneNumber;
    }

    @Bindable
    public String getEmail() {
        return mEmail;
    }

    @Bindable
    public String getVkProfile() {
        return mVkProfile;
    }

    @Bindable
    public String getRepository() {
        return mRepository;
    }

    @Bindable
    public String getAbout() {
        return mAbout;
    }

    @Bindable
    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    @Bindable
    public String getPhotoUrl() {
        return mPhotoUrl;
    }

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

    // ---------- SETTERS ----------


    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(BR.name);
    }

    public void setRating(int rating) {
        mRating = String.valueOf(rating);
        notifyPropertyChanged(BR.rating);
    }

    public void setLinesCount(int linesCount) {
        mLinesCount = String.valueOf(linesCount);
        notifyPropertyChanged(BR.linesCount);
    }

    public void setProjectCount(int projectCount) {
        mProjectCount = String.valueOf(projectCount);
        notifyPropertyChanged(BR.projectCount);
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        mMobilePhoneNumber = mobilePhoneNumber;
        notifyPropertyChanged(BR.mobilePhoneNumber);
    }

    public void setEmail(String email) {
        mEmail = email;
        notifyPropertyChanged(BR.email);
    }

    public void setVkProfile(String vkProfile) {
        mVkProfile = vkProfile;
        notifyPropertyChanged(BR.vkProfile);
    }

    public void setRepository(String repository) {
        mRepository = repository;
        notifyPropertyChanged(BR.repository);
    }

    public void setAbout(String about) {
        mAbout = about;
        notifyPropertyChanged(BR.about);
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
        notifyPropertyChanged(BR.avatarUrl);
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
        notifyPropertyChanged(BR.photoUrl);
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