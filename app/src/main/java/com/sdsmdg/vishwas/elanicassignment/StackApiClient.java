package com.sdsmdg.vishwas.elanicassignment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by vishwas on 22/3/18.
 */

public interface StackApiClient {
    @GET("/2.2/questions/?order=desc&sort=activity&site=stackoverflow&filter=withbody")
    Call<QuestionClass> getQuestions(
            @Query("tagged") String tag
    );
}
