package com.softdesign.devintensive.data.network.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdesign.devintensive.common.BuildConfiguration;
import com.softdesign.devintensive.data.network.restmodels.BaseResponse;
import com.softdesign.devintensive.utils.L;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by skwmium on 27.06.16.
 */
public class ServiceGenerator {
    private static Retrofit.Builder sBuilder;
    private static Retrofit sRetrofit;

    static {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfiguration.HTTP_LOG_LEVEL);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        ObjectMapper jacksonObjectMapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        JacksonConverterFactory jacksonConverterFactory = JacksonConverterFactory
                .create(jacksonObjectMapper);

        sBuilder = new Retrofit.Builder()
                .addConverterFactory(jacksonConverterFactory)
                .client(httpClient);
    }

    public static SoftdesignClient createSoftdesignService() {
        if (sRetrofit == null) {
            sRetrofit = sBuilder
                    .baseUrl(SoftdesignClient.BASE_URL)
                    .build();
        }
        return sRetrofit.create(SoftdesignClient.class);
    }

    public static BaseResponse parseError(Response<?> response) {
        Converter<ResponseBody, BaseResponse> converter = sRetrofit
                .responseBodyConverter(BaseResponse.class, new Annotation[0]);
        try {
            return converter.convert(response.errorBody());
        } catch (IOException e) {
            L.e(e);
            return new BaseResponse();
        }
    }
}
