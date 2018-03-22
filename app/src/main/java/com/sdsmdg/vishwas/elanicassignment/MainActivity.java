package com.sdsmdg.vishwas.elanicassignment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sdsmdg.vishwas.elanicassignment.PresentarClass.getData;
import static com.sdsmdg.vishwas.elanicassignment.PresentarClass.setUpRestClient;

public class MainActivity extends AppCompatActivity {

    private ImageView statusImage;
    private Toolbar toolbar;
    private RecyclerView questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PresentarClass.initiateActivity(MainActivity.this);
    }

    public void showSplash(){
        setContentView(R.layout.splash_screen);
    }

    public void startMainActivity(){
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        statusImage = findViewById(R.id.status_image);
        questionList = findViewById(R.id.question_list);
        questionList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        getData(MainActivity.this, "Android");
    }

    public void setAdapter(QuestionClass questions){
        questionList.setAdapter(new QuestionListAdapter(MainActivity.this, questions));
    }

    public void noResultFound(){
        statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.no_result_found));
    }

    public void httpError(){
        statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.http_error));
    }

    public void clearImage() {
        statusImage.setImageResource(0);
    }

    public void connectionError(){
        statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.connection_error));
    }

    public void unknownError(){
        statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sad_icon));
    }

//    @Override
//    public Intent getIntent() {
//        return super.getIntent();
//    }
//
//    void temp(){
////        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
////        Context context = MainActivity.this;
////        Intent intent = getIntent();
////        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
////            String query = intent.getStringExtra(SearchManager.QUERY);
////            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
////                    SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
////            suggestions.saveRecentQuery(query, null);
////            Log.e("search query", query);
////            getData(query);
////        } else {
////            getData("Android");
////        }
//    }

//    private void getData(String query) {
////        String API_BASE_URL = "https://api.stackexchange.com";
////
////        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
////
////        Retrofit.Builder builder = new Retrofit.Builder()
////                .baseUrl(API_BASE_URL)
////                .addConverterFactory(GsonConverterFactory.create());
////
////        Retrofit retrofit = builder.client(httpClient.build()).build();
////        StackApiClient client = retrofit.create(StackApiClient.class);
//        StackApiClient client = setUpRestClient();
//        Call<QuestionClass> call = client.getQuestions(query);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager != null ? searchManager.getSearchableInfo(getComponentName()) : null);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryRefinementEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                getData(MainActivity.this, query);
                return true;
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
