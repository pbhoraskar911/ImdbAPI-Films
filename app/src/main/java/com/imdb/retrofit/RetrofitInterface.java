package com.imdb.retrofit;

import com.imdb.model.ImdbResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by prana on 6/30/2016.
 */

public interface RetrofitInterface {

    @GET("imdb/inTheaters")
    Call<ImdbResponse> queryImdbApi(@QueryMap Map<String, String> queryMap);
}
