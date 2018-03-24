package com.sdsmdg.vishwas.elanicassignment.presenters;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sdsmdg.vishwas.elanicassignment.R;
import com.sdsmdg.vishwas.elanicassignment.views.MainActivity;
import com.sdsmdg.vishwas.elanicassignment.interfaces.StackApiClient;
import com.sdsmdg.vishwas.elanicassignment.models.QuestionClass;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PresenterClass {

    private static String ORDER_BY = "desc";
    private static String SORT = "activity";
    private static final String DEFAULT_SEARCH = "android";

    public static void startActivity(final MainActivity object, String query) {
        if (query == null) {
            object.showSplash();

            final Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    object.startMainActivity(DEFAULT_SEARCH);
                }
            };
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                }
            });
            thread.start();
        } else {
            object.startMainActivity(query);
        }
    }

    private static StackApiClient setUpRestClient() {
        String API_BASE_URL = "https://api.stackexchange.com";
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(StackApiClient.class);
    }

    public static void getData(final MainActivity context, String query, String sort, String order_by) {
        SORT = (sort != null) ? sort : SORT;
        ORDER_BY = (order_by != null) ? order_by : ORDER_BY;

//        context.temp(getMenuItemID(ORDER_BY));
//        context.temp(getMenuItemID(SORT));

        context.clearScreen();
        context.showProgressBar();
        StackApiClient client = setUpRestClient();
        Call<QuestionClass> call = client.getQuestions(query, SORT, ORDER_BY);
        call.enqueue(new Callback<QuestionClass>() {
            @Override
            public void onResponse(Call<QuestionClass> call, Response<QuestionClass> response) {
                Log.e("order_by", ORDER_BY);
                Log.e("sort", SORT);
                context.temp(getMenuItemID(ORDER_BY));
                context.temp(getMenuItemID(SORT));
                context.hideProgressBar();
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
                    context.clearAdapter();
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

    public static int getMenuItemID(String itemString) {
        switch (itemString) {
            case "asc":
                return R.id.ascending;
            case "desc":
                return R.id.descending;
            case "activity":
                return R.id.activity;
            case "votes":
                return R.id.votes;
            case "creation":
                return R.id.creation;
            case "hot":
                return R.id.hot;
            case "week":
                return R.id.week;
            case "month":
                return R.id.month;
            default:
                return 0;
        }
    }

}
