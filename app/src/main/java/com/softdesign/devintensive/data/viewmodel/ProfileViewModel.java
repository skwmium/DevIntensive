package com.softdesign.devintensive.data.viewmodel;

import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.softdesign.devintensive.BR;
import com.squareup.picasso.Picasso;

/**
 * Created by skwmium on 23.06.16.
 * <p>
 * Приведение данных к необходимому для отображения виду происходит в сеттерах,
 * тк Data Binding позволяет менять модель не из UI потока, и в этом случае преобразования
 * не будут тормозить ui
 */
public class ProfileViewModel extends BaseViewModel implements EditableModel {

    //TODO remove this method after test
    public static ProfileViewModel createTestProfile() {
        ProfileViewModel model = new ProfileViewModel();
        model.setRating(5);
        model.setLinesCount(25000);
        model.setProjectCount(5);
        model.setMobilePhoneNumber("+7 (###) ###-##-##");
        model.setEmail("skwmium@gmail.com");
        model.setVkProfile("vk.com/skwmium");
        model.setRepository("https://github.com/skwmium/DevIntensive");
        model.setAvatarUrl("http://");
        model.setAbout("So close no matter how far\n" +
                "Couldn't be much more from the heart\n" +
                "Forever trusting who we are\n" +
                "And nothing else matters\n" +
                "\n" +
                "Never opened myself this way\n" +
                "Life is ours, we live it our way\n" +
                "All these words I don't just say\n" +
                "And nothing else matters\n" +
                "\n" +
                "Trust I seek and I find in you\n" +
                "Every day for us something new\n" +
                "Open mind for a different view\n" +
                "And nothing else matters\n" +
                "\n" +
                "Never cared for what they do\n" +
                "Never cared for what they know\n" +
                "But I know");
        return model;
    }

    private String mRating;
    private String mLinesCount;
    private String mProjectCount;
    private String mMobilePhoneNumber;
    private String mEmail;
    private String mVkProfile;
    private String mRepository;
    private String mAbout;
    private String mAvatarUrl;
    private boolean mIsEditable;

    @BindingAdapter({"bind:imageUrl", "bind:placeholder"})
    public static void loadImage(ImageView view, String url, Drawable placeholder) {
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(placeholder)
                .into(view);
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

    @Override
    @Bindable
    public boolean isEditable() {
        return mIsEditable;
    }

    // ---------- SETTERS ----------

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

    public void setEditable(boolean editable) {
        mIsEditable = editable;
        notifyPropertyChanged(BR.editable);
    }
}