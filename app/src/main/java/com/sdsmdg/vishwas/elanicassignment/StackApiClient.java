package com.sdsmdg.vishwas.elanicassignment;

import com.sdsmdg.vishwas.elanicassignment.models.QuestionClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface StackApiClient {
    @GET("/2.2/questions/?order=desc&sort=activity&site=stackoverflow&filter=withbody")
    Call<QuestionClass> getQuestions(
            @Query("tagged") String tag
    );
}
