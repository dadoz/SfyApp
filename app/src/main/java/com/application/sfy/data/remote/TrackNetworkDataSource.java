package com.application.mxm.soundtracks.data.remote;

import com.application.mxm.soundtracks.data.TrackDataSource;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.data.remote.services.RetrofitServiceRx;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by davide-syn on 4/24/18.
 */

@Singleton
public class TrackNetworkDataSource extends RetrofitDataSourceBase implements TrackDataSource {
    /**
     *
     * @param pages
     * @param pageSize
     * @param country
     * @param fHasLyrics
     * @param apiKey
     * @return
     */
    public Observable<List<Track>> getTracks(Integer[] pages, String pageSize, String country, String fHasLyrics, String apiKey) {
        return new RetrofitServiceRx().getSoundtrackRetrofit()
                .getTracks(Integer.toString(pages[0]), pageSize, country, fHasLyrics, apiKey)
                .compose(handleRxErrorsTransformer());
    }

    /**
     * @param stargazers
     * @param key
     */
    @Override
    public void setTracks(List<Track> stargazers, String key) {
    }

    @Override
    public boolean hasTracks(String paramsKey) {
        return false;
    }
}
