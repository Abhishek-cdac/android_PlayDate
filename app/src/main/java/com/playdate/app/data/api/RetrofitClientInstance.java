package com.playdate.app.data.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class
RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://139.59.0.106:3000/api/";
    public static final String BASE_URL_IMAGE = "http://139.59.0.106:3000";
    public static final String DEVICE_TYPE = "Android";


    private static OkHttpClient getRequestHeader() {
        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getRequestHeader())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
