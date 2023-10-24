package com.example.sembakomobile.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String baseURL = "http://sofiyatul.kencang.id/api2/";
    private static Retrofit retro;
    private static OkHttpClient.Builder builder = new OkHttpClient.Builder();
    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

    public static Retrofit getClient(){
        if(retro == null){
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retro = new Retrofit.Builder().baseUrl(baseURL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retro;
    }
}
