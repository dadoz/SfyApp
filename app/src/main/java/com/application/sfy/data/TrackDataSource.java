package com.application.sfy.data;


import com.application.sfy.data.model.Track;

import java.util.List;

import io.reactivex.Observable;

public interface TrackDataSource {
    Observable<List<Track>> getTracks(Integer[] pages, String pageSize, String country, String fHasLyrics, String apiKey);
    void setTracks(List<Track> stargazers, String paramsKey);
    boolean hasTracks(String paramsKey);
}
