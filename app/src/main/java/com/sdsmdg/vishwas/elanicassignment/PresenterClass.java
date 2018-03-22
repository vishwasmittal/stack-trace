package com.sdsmdg.vishwas.elanicassignment;

//import android.os.Looper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PresenterClass {

    static void startApp(final MainActivity object) {
        object.showSplash();

        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                object.startMainActivity();
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        });
        thread.start();
    }

    static StackApiClient setUpRestClient() {
        String API_BASE_URL = "https://api.stackexchange.com";
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(StackApiClient.class);
    }

    public static void getData(final MainActivity context, String query) {
        StackApiClient client = setUpRestClient();
        Call<QuestionClass> call = client.getQuestions(query);
        call.enqueue(new Callback<QuestionClass>() {
            @Override
            public void onResponse(Call<QuestionClass> call, Response<QuestionClass> response) {
                if (response.isSuccessful()) {
                    QuestionClass questions = response.body();
                    Log.e("onResponse", "Success, items" + String.valueOf(questions.getSize()));
                    if (questions.getSize() == 0) {
                        context.noResultFound();
                    } else {
                        context.clearImage();
                    }
                    context.setAdapter(questions);
                } else {
                    Log.e("onResponse", "response unsuccessful, " + response.toString());
//                    TODO: make a clear adapter method
                    context.httpError();
                }
            }

            @Override
            public void onFailure(Call<QuestionClass> call, Throwable t) {
                t.printStackTrace();
                if (t instanceof HttpException) {
                    Log.e("onFailure", "HTTP Exception");
                    context.httpError();
                } else if (t instanceof IOException) {
                    Log.e("onFailure", "IOError");
                    context.connectionError();
                } else {
                    context.unknownError();
                }
            }
        });
    }
}
