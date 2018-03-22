package com.sdsmdg.vishwas.elanicassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String API_BASE_URL = "https://api.stackexchange.com";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        StackApiClient client = retrofit.create(StackApiClient.class);

        Call<QuestionClass> call = client.getQuestions();

        call.enqueue(new Callback<QuestionClass>() {
            @Override
            public void onResponse(Call<QuestionClass> call, Response<QuestionClass> response) {
                Log.e("Retrofit success", response.toString());
//                response.
            }

            @Override
            public void onFailure(Call<QuestionClass> call, Throwable t) {
                Log.e("Call failed", t.toString());
            }
        });


    }
}
