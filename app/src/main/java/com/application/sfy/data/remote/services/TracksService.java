package com.application.sfy.data.remote.services;


import com.application.sfy.data.model.Lyric;
import com.application.sfy.data.model.Track;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TracksService {
    @GET("chart.tracks.get")
    Observable<List<Track>> getTracks(@Query("page") String page, @Query("page_size") String pageSize,
            @Query("country") String country, @Query("f_has_lyrics") String fHasLyrics, @Query("apikey") String apiKey);

    @GET("track.lyrics.get")
    Observable<Lyric> getLyrics(@Query("track_id") String trackId, @Query("apikey") String apiKey);
}
