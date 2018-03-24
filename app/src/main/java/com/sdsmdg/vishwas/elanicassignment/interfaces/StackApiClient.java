package com.sdsmdg.vishwas.elanicassignment.interfaces;

import com.sdsmdg.vishwas.elanicassignment.models.QuestionClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface StackApiClient {
    @GET("/2.2/questions/?site=stackoverflow&filter=withbody")
    Call<QuestionClass> getQuestions(
            @Query("tagged") String tag,
            @Query("sort") String sort,
            @Query("order") String order,
            @Query("page") int page
    );
}
