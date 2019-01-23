package com.application.sfy.data;


import com.application.sfy.data.model.Track;

import java.util.List;

import io.reactivex.Observable;

public interface TrackDataSource {
    Observable<List<Track>> getTracks(int pages, String trackName, String apiKey);
    Observable<List<Track>> getTracks(String trackName, String apiKey);
    void setTracks(List<Track> stargazers, String paramsKey);
    boolean hasTracks(String paramsKey);
}
