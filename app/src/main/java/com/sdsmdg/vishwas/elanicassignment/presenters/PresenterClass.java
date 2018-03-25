package com.sdsmdg.vishwas.elanicassignment.presenters;

import android.util.Log;

import com.sdsmdg.vishwas.elanicassignment.AsyncTaskHandlers;
import com.sdsmdg.vishwas.elanicassignment.Helpers;
import com.sdsmdg.vishwas.elanicassignment.Settings;
import com.sdsmdg.vishwas.elanicassignment.interfaces.IActivityView;
import com.sdsmdg.vishwas.elanicassignment.interfaces.StackApiClient;
import com.sdsmdg.vishwas.elanicassignment.interfaces.TaskCompleteListener;
import com.sdsmdg.vishwas.elanicassignment.models.QuestionClass;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;


public class PresenterClass implements TaskCompleteListener {

    private String ORDER_BY = "desc";
    private String SORT = "activity";
    private IActivityView view;
    private AsyncTaskHandlers asyncTaskHandlers;
    private Helpers helpers;

    public PresenterClass(IActivityView view) {
        this.view = view;
        asyncTaskHandlers = new AsyncTaskHandlers();
        helpers = new Helpers();
    }

    public void startActivity(String query) {
        if (query == null) {
            view.showSplash();
            asyncTaskHandlers.callMainActivityAsync(this);
        } else {
            view.startMainActivity(query);
            view.setDisplayHomeAsUpEnabled();
        }
    }

    public void getData(String query, String sort, String order_by, final int page) {
        SORT = (sort != null) ? sort : SORT;
        ORDER_BY = (order_by != null) ? order_by : ORDER_BY;
        if (page == 1) view.clearScreen();
        view.showProgressBar();
        asyncTaskHandlers.getDataAsync(this, query, SORT, ORDER_BY, page);
    }


    @Override
    public void startMainActivity() {
        view.startMainActivity(Settings.DEFAULT_SEARCH);
    }

    @Override
    public void setMenuItemSelected(String sort, String orderby) {
        view.setMenuItemSelected(helpers.getMenuItemIDFromString(sort));
    }

    @Override
    public void noResultFound() {
        view.noResultFound();
    }

    @Override
    public void clearImage() {
        view.clearImage();
    }

    @Override
    public void addItems(QuestionClass questions) {
        view.addItems(questions);
    }

    @Override
    public void clearAdapter() {
        view.clearAdapter();
    }

    @Override
    public void httpError() {
        view.httpError();
    }

    @Override
    public void hideProgressBar() {
        view.hideProgressBar();
    }

    @Override
    public void connectionError() {
        view.connectionError();
    }

    @Override
    public void unknownError() {
        view.unknownError();
    }
}
