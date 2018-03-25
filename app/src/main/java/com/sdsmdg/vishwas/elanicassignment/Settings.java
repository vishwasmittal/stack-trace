package com.sdsmdg.vishwas.elanicassignment;

import com.sdsmdg.vishwas.elanicassignment.interfaces.StackApiClient;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Settings {

    // Constants
    private static final String API_BASE_URL = "https://api.stackexchange.com";
    public static final String DEFAULT_SEARCH = "android";

    static StackApiClient setUpRestClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(StackApiClient.class);
    }
}
