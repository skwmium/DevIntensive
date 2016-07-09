package com.softdesign.devintensive.data.network.api;

import android.support.annotation.NonNull;

import com.softdesign.devintensive.data.network.dto.AuthResult;
import com.softdesign.devintensive.data.network.dto.BaseResponse;
import com.softdesign.devintensive.data.network.dto.EditProfileResult;
import com.softdesign.devintensive.data.network.dto.User;
import com.softdesign.devintensive.data.network.params.ParamAuth;
import com.softdesign.devintensive.data.network.params.ParamEdit;
import com.softdesign.devintensive.data.network.params.ParamForgotPassword;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by skwmium on 27.06.16.
 */
public interface SoftdesignApiClient {

    // ---------- USER ----------

    @POST("login")
    Observable<BaseResponse<AuthResult>> userAuth(@NonNull @Body ParamAuth authParam);

    @POST("sendforgot")
    Observable<BaseResponse> userRestorePassword(@NonNull @Body ParamForgotPassword paramForgotPassword);

    @POST("profile/edit")
    Observable<BaseResponse<EditProfileResult>> userEdit(@NonNull @Body ParamEdit editParam);

    @GET("user/{userId}")
    Observable<BaseResponse<User>> userGet(@NonNull @Path("userId") String userId);
}
