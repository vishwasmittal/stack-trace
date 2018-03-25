package com.sdsmdg.vishwas.elanicassignment;

import android.os.Handler;

import com.sdsmdg.vishwas.elanicassignment.interfaces.StackApiClient;
import com.sdsmdg.vishwas.elanicassignment.interfaces.TaskCompleteListener;
import com.sdsmdg.vishwas.elanicassignment.models.QuestionClass;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;


public class AsyncTaskHandlers {

    public void callMainActivityAsync(final TaskCompleteListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.startMainActivity();
            }
        }, 2000);
    }

    public void getDataAsync(final TaskCompleteListener listener, String query, final String sort, final String orderby, final int page) {
        StackApiClient client = Settings.setUpRestClient();
        Call<QuestionClass> call = client.getQuestions(query, sort, orderby, page);
        call.enqueue(new Callback<QuestionClass>() {
            @Override
            public void onResponse(Call<QuestionClass> call, Response<QuestionClass> response) {
                listener.setMenuItemSelected(sort, orderby);
                listener.hideProgressBar();
                if (response.isSuccessful()) {
                    QuestionClass questions = response.body();
                    if (questions.getSize() == 0 && page == 1) {
                        listener.noResultFound();
                    } else {
                        listener.clearImage();
                    }
                    listener.addItems(questions);
                } else {
                    listener.clearAdapter();
                    listener.httpError();
                }
            }

            @Override
            public void onFailure(Call<QuestionClass> call, Throwable t) {
                t.printStackTrace();
                if (t instanceof HttpException) {
                    listener.httpError();
                } else if (t instanceof IOException) {
                    listener.connectionError();
                } else {
                    listener.unknownError();
                }
            }
        });
    }
}
