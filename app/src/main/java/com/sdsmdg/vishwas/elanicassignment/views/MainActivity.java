package com.sdsmdg.vishwas.elanicassignment.views;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.sdsmdg.vishwas.elanicassignment.EndlessRecyclerViewScrollListener;
import com.sdsmdg.vishwas.elanicassignment.SuggestionProvider;
import com.sdsmdg.vishwas.elanicassignment.adapters.QuestionListAdapter;
import com.sdsmdg.vishwas.elanicassignment.R;
import com.sdsmdg.vishwas.elanicassignment.interfaces.IActivityView;
import com.sdsmdg.vishwas.elanicassignment.models.QuestionClass;
import com.sdsmdg.vishwas.elanicassignment.presenters.PresenterClass;


public class MainActivity extends AppCompatActivity implements IActivityView {

    private ImageView statusImage;
    private ProgressBar progressBar;
    private QuestionListAdapter questionListAdapter;
    private QuestionClass questions;
    private Menu menu;
    private String query;
    private EndlessRecyclerViewScrollListener scrollListener;
    private PresenterClass presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        query = getSearchQuery();
        presenter = new PresenterClass(this);
        presenter.startActivity(query);
    }

    public String getSearchQuery() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            return query;
        } else {
            return null;
        }
    }

    public void showSplash() {
        setContentView(R.layout.splash_screen);
    }

    public void setDisplayHomeAsUpEnabled() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(query);
    }


    public void startMainActivity(final String query) {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        statusImage = findViewById(R.id.status_image);
        progressBar = findViewById(R.id.simpleProgressBar);
        RecyclerView questionList = findViewById(R.id.question_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        questionList.setLayoutManager(layoutManager);
        questions = new QuestionClass();
        questionListAdapter = new QuestionListAdapter(MainActivity.this, questions);
        questionList.setAdapter(questionListAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.getData(query, null, null, page);
            }
        };
        questionList.addOnScrollListener(scrollListener);
        presenter.getData(query, null, null, 1);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void addItems(QuestionClass questions) {
        this.questions.addItems(questions.getItems());
        questionListAdapter.notifyDataSetChanged();
    }

    public void clearAdapter() {
        questions.clear();
        questionListAdapter.notifyDataSetChanged();
    }

    public void clearScreen() {
        clearImage();
        clearAdapter();
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public void setMenuItemText(String sort, String orderby) {
        MenuItem sortItem = menu.findItem(R.id.sort);
        MenuItem orderbyItem = menu.findItem(R.id.order_by);
        sortItem.setTitle(getString(R.string.sort) + " - " + sort.toUpperCase().charAt(0) + sort.substring(1, sort.length()));
        orderbyItem.setTitle(getString(R.string.order_by) + " - " + orderby.toUpperCase().charAt(0) + orderby.substring(1, orderby.length()));
    }

    @Override
    public void setMenuItemSelected(int id) {
        try {
            MenuItem menuItem = menu.findItem(id);
            menuItem.setChecked(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String orderby = null;
        String sort = null;
        switch (item.getItemId()) {
            case R.id.search:
                return true;
            case R.id.activity:
                sort = "activity";
                break;
            case R.id.votes:
                sort = "votes";
                break;
            case R.id.creation:
                sort = "creation";
                break;
            case R.id.hot:
                sort = "hot";
                break;
            case R.id.week:
                sort = "week";
                break;
            case R.id.month:
                sort = "month";
                break;
            case R.id.ascending:
                orderby = "asc";
                break;
            case R.id.descending:
                orderby = "desc";
                break;
            default:
                return false;
        }
        scrollListener.resetState();
        presenter.getData(query, sort, orderby, 1);
        return true;
    }
}
