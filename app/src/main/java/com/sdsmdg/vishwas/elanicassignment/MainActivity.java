package com.sdsmdg.vishwas.elanicassignment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        Call<QuestionClass> call = client.getQuestions("Android");

        final RecyclerView questionList = findViewById(R.id.question_list);
        questionList.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        call.enqueue(new Callback<QuestionClass>() {
            @Override
            public void onResponse(Call<QuestionClass> call, Response<QuestionClass> response) {
                if (response.isSuccessful()) {
                    QuestionClass questions = response.body();
                    Log.e("onResponse", "Success, items" + String.valueOf(questions.getSize()));
                    questionList.setAdapter(new QuestionListAdapter(getApplicationContext(), questions));
                } else {
                    Log.e("onResponse", "response unsuccessful");
                }
            }

            @Override
            public void onFailure(Call<QuestionClass> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
