package com.project.semicolon.rxjava.services;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.project.semicolon.rxjava.Const;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static final String TAG = ApiClient.class.getSimpleName();
    private static int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;

    public static Retrofit getClient() {
        if (okHttpClient == null)
            initHttpClient();

        if (retrofit == null) {
            retrofit = initRetrofit();
        }

        return retrofit;

    }

    private static Retrofit initRetrofit() {
        return new Retrofit.Builder().baseUrl(Const.BASE_URL).client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static void initHttpClient() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder.connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        httpBuilder.readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        httpBuilder.writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        httpBuilder.addInterceptor(interceptor);

        httpBuilder.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.addHeader("Accept", "application/json");
                requestBuilder.addHeader("Request-Type", "Android");
                requestBuilder.addHeader("Content-Type", "application/json");

                Request request = requestBuilder.build();


                return chain.proceed(request);
            }
        });

        okHttpClient = httpBuilder.build();
    }
}
