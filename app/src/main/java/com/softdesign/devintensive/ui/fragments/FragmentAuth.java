package com.softdesign.devintensive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.di.DaggerComponentAuth;
import com.softdesign.devintensive.di.ModuleViewAuth;
import com.softdesign.devintensive.presenter.BasePresenter;
import com.softdesign.devintensive.presenter.PresenterAuth;
import com.softdesign.devintensive.view.ViewAuth;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by skwmium on 01.07.16.
 */
public class FragmentAuth extends BaseFragment implements ViewAuth {
    @BindView(R.id.edit_email)
    EditText editEmail;

    @BindView(R.id.edit_password)
    EditText editPassword;

    @BindView(R.id.button_login)
    Button buttonLogin;

    @BindView(R.id.button_try_demo)
    Button buttonTryDemo;

    @Inject
    PresenterAuth mAuthPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerComponentAuth
                .builder()
                .moduleViewAuth(new ModuleViewAuth(this))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonLogin.setOnClickListener(view1 -> mAuthPresenter.loginClicked());
        buttonTryDemo.setOnClickListener(view2 -> mAuthPresenter.demoModeClicked());
    }

    @Nullable
    @Override
    protected BasePresenter getPresenter() {
        return mAuthPresenter;
    }

    @Override
    public String getEmail() {
        return editEmail.getText().toString();
    }

    @Override
    public String getPassword() {
        return editPassword.getText().toString();
    }

    @Override
    public void showMessage(@StringRes int res) {
        getBaseActivity().showSnackbar(res);
    }

    @Override
    public void showProgress() {
        getBaseActivity().showProgress();
    }

    @Override
    public void hideProgress() {
        getBaseActivity().hideProgress();
    }
}
