package com.application.mxm.soundtracks.data;

import com.application.mxm.soundtracks.BuildConfig;
import com.application.mxm.soundtracks.data.local.Local;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.data.remote.Remote;
import com.application.mxm.soundtracks.utils.Utils;

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
    public Observable<List<Track>> getTracks(Integer[] pages, String pageSize, String country, String fHasLyrics) {
        //set params key
        String paramsKey = Utils.getTrackParamsKey(Integer.toString(pages[pages.length -1]), pageSize, country, fHasLyrics);

        //show data from cache
       if (localDataSource.hasTracks(paramsKey)) {
            return localDataSource.getTracks(pages, pageSize, country, fHasLyrics, BuildConfig.API_KEY);
        }

        //show data from netwkor and added on cache if some result
        return networkDataSource
                .getTracks(pages, pageSize, country, fHasLyrics, BuildConfig.API_KEY)
                .doOnNext(list -> localDataSource.setTracks(list, paramsKey));
    }

    public void refreshCache() {
        //TODO implement it
    }
}
