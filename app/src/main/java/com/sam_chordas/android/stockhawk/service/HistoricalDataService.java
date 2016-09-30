package com.sam_chordas.android.stockhawk.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sam_chordas.android.stockhawk.historicaldata.HistoricalData;


import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by v.maletskiy on 12.09.2016.
 */

public interface HistoricalDataService {
    @GET("v1/public/yql")
    Call<HistoricalData> getData(@Query("q") String query, @Query("env") String env, @Query("format") String format);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://query.yahooapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
