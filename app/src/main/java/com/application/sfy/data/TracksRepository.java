package com.application.sfy.data;

import com.application.sfy.BuildConfig;
import com.application.sfy.data.local.Local;
import com.application.sfy.data.model.Track;
import com.application.sfy.data.remote.Remote;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by davide-syn on 4/24/18.
 */

public class TracksRepository {

    private final TrackDataSource localDataSource;
    private final TrackDataSource networkDataSource;

    @Inject
    TracksRepository(@Local TrackDataSource localDataSource, @Remote TrackDataSource networkDataSource) {
        this.localDataSource = localDataSource;
        this.networkDataSource = networkDataSource;
    }

    /**re
     * get cached or network data
     * @return
     */
    public Observable<List<Track>> getTracks(String trackName) {

        //show data from cache
       if (localDataSource.hasTracks(trackName)) {
            return localDataSource.getTracks(trackName, BuildConfig.API_KEY);
        }

        //show data from netwkor and added on cache if some result
        return networkDataSource
                .getTracks(trackName, BuildConfig.API_KEY)
                .doOnNext(list -> localDataSource.setTracks(list, trackName));
    }

    /**
     * TODO implement pagination
     * @param page
     * @param trackName
     * @return
     */
    public Observable<List<Track>> getTracks(int page, String trackName) {
        return null;
    }

    public void refreshCache() {
        //TODO implement it
    }
}
