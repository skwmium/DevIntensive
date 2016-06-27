package com.softdesign.devintensive.data.network.api;

import android.support.annotation.NonNull;

import com.softdesign.devintensive.data.network.params.AuthParam;
import com.softdesign.devintensive.data.network.params.EditParam;
import com.softdesign.devintensive.data.network.restmodels.AuthResult;
import com.softdesign.devintensive.data.network.restmodels.BaseResponse;
import com.softdesign.devintensive.data.network.restmodels.EditProfileResult;
import com.softdesign.devintensive.data.network.restmodels.Profile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by skwmium on 27.06.16.
 */
public interface SoftdesignClient {
    String BASE_URL = "http://devintensive.softdesign-apps.ru/api/";


    // ---------- USER ----------

    @POST("login")
    Call<BaseResponse<AuthResult>> userAuth(@NonNull @Body AuthParam authParam);

    @POST("profile/edit")
    Call<BaseResponse<EditProfileResult>> userEdit(@NonNull @Query("token") String token,
                                                   @NonNull @Body EditParam editParam);

    //TODO change this method
    //This method is like hack, I use it because I didn't find method GET /profile
    @POST("profile/edit")
    Call<BaseResponse<Profile>> userGet(@NonNull @Query("token") String token);
}
