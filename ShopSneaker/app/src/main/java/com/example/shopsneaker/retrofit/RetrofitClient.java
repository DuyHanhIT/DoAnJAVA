package com.example.shopsneaker.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    static Gson gson = new GsonBuilder()
            .setLenient()
            .setDateFormat ("yyyy-MM-dd HH:mm:ss")
            .create();
    private static retrofit2.Retrofit instance;
    public  static retrofit2.Retrofit getInstance(String  baseUrl){
        if (instance == null){
            instance = new retrofit2.Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory.create())
                    .build();
        }
        return instance;
    }
}
