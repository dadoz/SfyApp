package com.application.sfy.data.remote.services;


import com.application.sfy.data.model.Track;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TracksService {
    @GET("chart.tracks.get")
    Observable<List<Track>> getTracks(@Query("q") String trackName, @Query("apikey") String apiKey);
}
