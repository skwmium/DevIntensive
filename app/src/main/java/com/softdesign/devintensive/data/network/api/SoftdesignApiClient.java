package com.softdesign.devintensive.data.network.api;

import android.support.annotation.NonNull;

import com.softdesign.devintensive.data.network.dto.AuthResult;
import com.softdesign.devintensive.data.network.dto.BaseResponse;
import com.softdesign.devintensive.data.network.dto.EditProfileResult;
import com.softdesign.devintensive.data.network.dto.UploadImageResult;
import com.softdesign.devintensive.data.network.dto.User;
import com.softdesign.devintensive.data.network.params.ParamAuth;
import com.softdesign.devintensive.data.network.params.ParamForgotPassword;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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

    @Multipart
    @POST("profile/edit")
    Observable<BaseResponse<EditProfileResult>> userEdit(@NonNull @PartMap Map<String, RequestBody> bodyMap);

    @Multipart
    @POST("user/{userId}/publicValues/profilePhoto")
    Observable<BaseResponse<UploadImageResult>> userUploadPhoto(@NonNull @Path("userId") String userId,
                                                                @NonNull @Part MultipartBody.Part filePhoto);

    @Multipart
    @POST("user/{userId}/publicValues/profileAvatar")
    Observable<BaseResponse<UploadImageResult>> userUploadAvatar(@NonNull @Path("userId") String userId,
                                                                 @NonNull @Part MultipartBody.Part fileAvatar);

    @GET("user/{userId}")
    Observable<BaseResponse<User>> userGet(@NonNull @Path("userId") String userId);

    @GET("user/list?orderBy=rating")
    Observable<BaseResponse<List<User>>> userGetList();
}
