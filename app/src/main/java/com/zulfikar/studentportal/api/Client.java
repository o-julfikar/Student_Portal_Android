package com.zulfikar.studentportal.api;

import android.content.Context;

import com.zulfikar.studentportal.account.SessionManager;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static final HashMap<Context, Retrofit> RETROFIT = new HashMap<>();
    private static final HashMap<Context, OkHttpClient> OK_HTTP_CLIENT = new HashMap<>();

    public static OkHttpClient getClient(Context context) {
        if (OK_HTTP_CLIENT.get(context) == null) {
            OK_HTTP_CLIENT.put(context, new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Cookie", "spsid=" + SessionManager.getSessionKey(context))
                                .build();
                        return chain.proceed(newRequest);
                    })
                    .build()
            );
        }
        return OK_HTTP_CLIENT.get(context);
    }

    public static Retrofit getRetrofit(Context context) {
        if (RETROFIT.get(context) == null) {
            RETROFIT.put(context, new Retrofit.Builder()
                    .baseUrl(URL.baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient(context))
                    .build()
            );
        }
        return RETROFIT.get(context);
    }

    public static JsonPlaceHolderApi getApi(Context context) {
        return getRetrofit(context).create(JsonPlaceHolderApi.class);
    }
}
