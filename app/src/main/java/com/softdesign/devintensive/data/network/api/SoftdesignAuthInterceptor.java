package com.softdesign.devintensive.data.network.api;

import com.softdesign.devintensive.data.storage.LocalUser;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by skwmium on 09.07.16.
 */
public class SoftdesignAuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder();
        String authToken = LocalUser.getInst().getAuthToken();
        if (authToken != null) builder.addHeader("X-Access-Token", authToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}
