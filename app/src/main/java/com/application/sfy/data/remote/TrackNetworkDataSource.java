package com.application.sfy.data.remote;

import com.application.sfy.data.TrackDataSource;
import com.application.sfy.data.model.Track;
import com.application.sfy.data.remote.services.RetrofitServiceRx;

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
     * @param apiKey
     * @return
     */
    public Observable<List<Track>> getTracks(int pages,String trackName, String apiKey) {
        return null;
    }

    public Observable<List<Track>> getTracks(String trackName, String apiKey) {
        return new RetrofitServiceRx().getSoundtrackRetrofit()
                .getTracks(trackName, apiKey)
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
