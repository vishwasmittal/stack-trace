package com.sdsmdg.vishwas.elanicassignment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.provider.SearchRecentSuggestions;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import static com.sdsmdg.vishwas.elanicassignment.PresenterClass.getData;

public class MainActivity extends AppCompatActivity {

    private ImageView statusImage;
    private Toolbar toolbar;
    private RecyclerView questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PresenterClass.startApp(MainActivity.this);
//        PresenterClass.initiateActivity(MainActivity.this);
    }

    public void showSplash() {
        setContentView(R.layout.splash_screen);
    }


    public void startMainActivity() {
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        statusImage = findViewById(R.id.status_image);
        questionList = findViewById(R.id.question_list);
        questionList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        getData(MainActivity.this, "Android");
    }

    public void setAdapter(QuestionClass questions) {
        questionList.setAdapter(new QuestionListAdapter(MainActivity.this, questions));
    }

    public void noResultFound() {
        statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.no_result_found));
    }

    public void httpError() {
        statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.http_error));
    }

    public void clearImage() {
        statusImage.setImageResource(0);
    }

    public void connectionError() {
        statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.connection_error));
    }

    public void unknownError() {
        statusImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sad_icon));
    }

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
