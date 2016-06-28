package com.softdesign.devintensive.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.softdesign.devintensive.R;
import com.softdesign.devintensive.data.managers.PreferencesManager;
import com.softdesign.devintensive.data.network.api.ServiceGenerator;
import com.softdesign.devintensive.data.network.api.SoftdesignClient;
import com.softdesign.devintensive.data.network.params.AuthParam;
import com.softdesign.devintensive.data.network.restmodels.AuthResult;
import com.softdesign.devintensive.data.network.restmodels.BaseResponse;
import com.softdesign.devintensive.utils.Const;
import com.softdesign.devintensive.utils.L;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by skwmium on 28.06.16.
 */
public class ActivityAuth extends BaseActivity {
    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context, ActivityAuth.class);
        context.startActivity(intent);
    }

    private EditText mEditEmail;
    private EditText mEditPassword;
    private Button mButtonLogin;
    private Button mButtonTryDemo;

    private View.OnClickListener mClickTryDemo = view -> {
        ActivityProfile.start(ActivityAuth.this);
    };

    private View.OnClickListener mClickLogin = view -> {
        processLogin();
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mEditEmail = $(R.id.edit_email);
        mEditPassword = $(R.id.edit_password);
        mButtonLogin = $(R.id.button_login);
        mButtonTryDemo = $(R.id.button_try_demo);

        mButtonLogin.setOnClickListener(mClickLogin);
        mButtonTryDemo.setOnClickListener(mClickTryDemo);
    }

    private void processLogin() {
        String email = mEditEmail.getText().toString();
        String password = mEditPassword.getText().toString();

        if (!checkAuthData(email, password)) return;
        authObServer(email, password);
    }

    private boolean checkAuthData(@Nullable String email, @Nullable String password) {
        if (email == null || email.isEmpty() || !email.contains("@")) {
            showSnackbar(R.string.auth_error_empty_email);
            return false;
        }
        if (password == null || password.isEmpty()) {
            showSnackbar(R.string.auth_error_empty_password);
            return false;
        }
        return true;
    }

    //TODO move this logic to data layer
    //TODO refactor
    private void authObServer(@NonNull final String email, @NonNull final String password) {
        showProgress();
        final AuthParam authParam = new AuthParam(email, password);
        final SoftdesignClient softdesignService = ServiceGenerator.createSoftdesignService();
        Call<BaseResponse<AuthResult>> baseResponseCall = softdesignService.userAuth(authParam);
        baseResponseCall.enqueue(new Callback<BaseResponse<AuthResult>>() {
            @Override
            public void onResponse(Call<BaseResponse<AuthResult>> call, Response<BaseResponse<AuthResult>> response) {
                BaseResponse<AuthResult> authResult;
                if (response.isSuccessful()) {
                    authResult = response.body();
                } else {
                    //noinspection unchecked
                    authResult = ServiceGenerator.parseError(response);
                }
                if (authResult == null) {
                    showSnackbar(R.string.auth_error_indefinite);
                } else if (!authResult.isSuccess() || authResult.getBody() == null
                        || authResult.getBody().getToken() == null
                        || authResult.getBody().getToken().isEmpty()) {
                    L.e("Auth fail! ", authResult.getError());
                    showSnackbar(R.string.auth_error_invalid_pass_or_email);
                } else {
                    L.e("Auth success! ");
                    PreferencesManager.getInst().put(Const.PREF_AUTH_TOKEN
                            , authResult.getBody().getToken());
                    ActivityProfile.start(ActivityAuth.this);
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<BaseResponse<AuthResult>> call, Throwable t) {
                showSnackbar(R.string.auth_error_indefinite);
                hideProgress();
            }
        });
    }
}
