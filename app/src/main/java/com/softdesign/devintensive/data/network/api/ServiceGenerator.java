package com.softdesign.devintensive.data.network.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdesign.devintensive.common.BuildConfiguration;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by skwmium on 27.06.16.
 */
public final class ServiceGenerator {
    private static final String SOFTDESIGN_BASE_URL = "http://devintensive.softdesign-apps.ru/api/";

    public static SoftdesignApiClient createSoftdesignApiInterface() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfiguration.HTTP_LOG_LEVEL);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        ObjectMapper jacksonObjectMapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        JacksonConverterFactory jacksonConverterFactory = JacksonConverterFactory
                .create(jacksonObjectMapper);

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(jacksonConverterFactory)
                .client(httpClient)
                .baseUrl(SOFTDESIGN_BASE_URL)
                .build()
                .create(SoftdesignApiClient.class);
    }
}
