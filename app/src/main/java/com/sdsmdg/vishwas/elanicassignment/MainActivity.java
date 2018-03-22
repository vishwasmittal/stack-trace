package com.sdsmdg.vishwas.elanicassignment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

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

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            Log.e("search query", query);
            getData(query);
        } else {
            getData("Android");
//            Call<QuestionClass> call = client.getQuestions("js");
//
//            final RecyclerView questionList = findViewById(R.id.question_list);
//            questionList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//
//            call.enqueue(new Callback<QuestionClass>() {
//                @Override
//                public void onResponse(Call<QuestionClass> call, Response<QuestionClass> response) {
//                    if (response.isSuccessful()) {
//                        QuestionClass questions = response.body();
//                        Log.e("onResponse", "Success, items" + String.valueOf(questions.getSize()));
//                        questionList.setAdapter(new QuestionListAdapter(questions));
//                    } else {
//                        Log.e("onResponse", "response unsuccessful");
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<QuestionClass> call, Throwable t) {
//                    t.printStackTrace();
//                }
//            });
        }
    }

    private void getData(String query) {
        String API_BASE_URL = "https://api.stackexchange.com";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();
        StackApiClient client = retrofit.create(StackApiClient.class);

        Call<QuestionClass> call = client.getQuestions(query);

        final RecyclerView questionList = findViewById(R.id.question_list);
        questionList.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        call.enqueue(new Callback<QuestionClass>() {
            @Override
            public void onResponse(Call<QuestionClass> call, Response<QuestionClass> response) {
                if (response.isSuccessful()) {
                    QuestionClass questions = response.body();
                    Log.e("onResponse", "Success, items" + String.valueOf(questions.getSize()));
                    questionList.setAdapter(new QuestionListAdapter(questions));
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager != null ? searchManager.getSearchableInfo(getComponentName()) : null);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onSearchClickListener", "it is activated");
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                finish();
                onBackPressed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public void clearSearchSuggestions() {
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
        suggestions.clearHistory();
    }
}
