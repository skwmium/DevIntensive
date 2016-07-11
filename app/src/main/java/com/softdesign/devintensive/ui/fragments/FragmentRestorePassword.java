package com.softdesign.devintensive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.di.DaggerComponentRestorePassword;
import com.softdesign.devintensive.di.ModuleRestorePassword;
import com.softdesign.devintensive.presenter.BasePresenter;
import com.softdesign.devintensive.presenter.PresenterRestorePassword;
import com.softdesign.devintensive.view.ViewRestorePassword;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by skwmium on 08.07.16.
 */
public class FragmentRestorePassword extends BaseFragment implements ViewRestorePassword {
    @BindView(R.id.edit_email)
    EditText editEmail;

    @BindView(R.id.button_restore)
    Button buttonRestore;

    @Inject
    PresenterRestorePassword mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerComponentRestorePassword
                .builder()
                .moduleRestorePassword(new ModuleRestorePassword(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restore_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonRestore.setOnClickListener(view1 -> mPresenter.restoreClicked());
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public String getEmail() {
        return editEmail.getText().toString();
    }

    @Override
    public void goBack() {
        getBaseActivity().onBackPressed();
    }
}
