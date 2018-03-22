package com.sdsmdg.vishwas.elanicassignment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vishwas on 22/3/18.
 */

public class PresentarClass {

//    public static void getData(Context context, String query) {
//        String API_BASE_URL = "https://api.stackexchange.com";
//
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl(API_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create());
//
//        Retrofit retrofit = builder.client(httpClient.build()).build();
//        StackApiClient client = retrofit.create(StackApiClient.class);
//
//        Call<QuestionClass> call = client.getQuestions(query);
//
//        final RecyclerView questionList = findViewById(R.id.question_list);
//        questionList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//
//        call.enqueue(new Callback<QuestionClass>() {
//            @Override
//            public void onResponse(Call<QuestionClass> call, Response<QuestionClass> response) {
//                if (response.isSuccessful()) {
//                    QuestionClass questions = response.body();
//                    Log.e("onResponse", "Success, items" + String.valueOf(questions.getSize()));
//                    if (questions.getSize() == 0){
//                        statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.no_result_found));
//                    }else {
//                        statusImage.setImageResource(0);
//                    }
//                    questionList.setAdapter(new QuestionListAdapter(MainActivity.this, questions));
//                } else {
//                    Log.e("onResponse", "response unsuccessful, " + response.toString());
//                    statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.http_error));
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<QuestionClass> call, Throwable t) {
//                t.printStackTrace();
//                if (t instanceof HttpException){
//                    Log.e("onFailure", "HTTP Exception");
//                    statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.http_error));
//                }
//                else if (t instanceof IOException) {
//                    Log.e("onFailure", "IOError");
//                    statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.connection_error));
//                } else {
//                    statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sad_icon));
//                }
//            }
//        });
//    }


}
