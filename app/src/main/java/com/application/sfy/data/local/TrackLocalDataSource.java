package com.application.mxm.soundtracks.data.local;


import android.util.Log;

import com.application.mxm.soundtracks.data.TrackDataSource;
import com.application.mxm.soundtracks.data.model.Track;
import com.application.mxm.soundtracks.data.model.TrackMap;
import com.application.mxm.soundtracks.utils.Utils;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;

/**
 * In Ram memory storage
 */
@Singleton
public class TrackLocalDataSource implements TrackDataSource {
    /**
     *
     * @param pages
     * @param pageSize
     * @param country
     * @param fHasLyrics
     * @param apiKey
     * @return
     */
    @Override
    public Observable<List<Track>> getTracks(Integer[] pages, String pageSize, String country, String fHasLyrics, String apiKey) {
        return Observable.range(pages[0], pages.length)// Integer.parseInt(page) == 1 ? Integer.parseInt(page) : Integer.parseInt(page) - 1) //counting from 1 to last page
                .compose(getPagedTracksTransformer(pageSize, country, fHasLyrics));
    }

    /**
     * has track check
     * @param paramsKey
     * @return
     */
    @Override
    public boolean hasTracks(String paramsKey) {
        try (Realm realm = Realm.getDefaultInstance()) {
            TrackMap cache = queryTrackMap(realm, paramsKey);
            return cache != null &&
                    cache.getTrackList() != null &&
                    cache.getTrackList().size() != 0;
        }
    }

    /**
     * set track on storage - realmio
     * @param trackList
     * @param paramsKey
     */
    @Override
    public void setTracks(List<Track> trackList, String paramsKey) {
        Log.i(getClass().getName(), "[PARAMS_KEY]" + paramsKey);
        try (Realm r = Realm.getDefaultInstance()) {
            r.executeTransaction(realm -> {
                //create track map
                TrackMap trackMap = realm.createObject(TrackMap.class, paramsKey);
                //adding new items
                trackMap.getTrackList().addAll(realm.copyToRealmOrUpdate(trackList));
            });
        }
    }

    /**
     * get tracks from storage
     * @param paramsKey
     * @return
     */
    public Observable<TrackMap> getTrackMap(String paramsKey) {
        return Observable.just(Realm.getDefaultInstance())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(realm -> queryTrackMap(realm, paramsKey)
                        .<TrackMap>asFlowable()
                        .toObservable());
    }

    /**
     * query trackMap
     * @param realm
     * @param paramsKey
     * @return
     */
    private TrackMap queryTrackMap(Realm realm, String paramsKey) {
        return realm.where(TrackMap.class)
                .equalTo("trackParamsKey", paramsKey)
                .findFirst();
    }

    /**
     * get paged tracks transformer
     * @param pageSize
     * @param country
     * @param fHasLyrics
     * @return
     */
    public ObservableTransformer<Integer, List<Track>> getPagedTracksTransformer(String pageSize,
                                                                                 String country, String fHasLyrics) {
        return obs -> obs
                .doOnNext(currentPage -> Log.e(getClass().getName(), "------- " + currentPage))
                .flatMap(currentPage -> Observable.just(Utils.getTrackParamsKey(Integer.toString(currentPage), pageSize, country, fHasLyrics))
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(params -> getTrackMap(params)
                                .map(trackMap -> {
                                    try (Realm realm = Realm.getDefaultInstance()) {
                                        //detach from realm -> since there's a problem with adding item in same list
                                        return realm.copyFromRealm(trackMap.getTrackList());
                                    }
                                })));
    }
}
