package com.application.sfy.data.local;


import android.util.Log;

import com.application.sfy.data.TrackDataSource;
import com.application.sfy.data.model.Track;
import com.application.sfy.data.model.TrackMap;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;

/**
 * In Ram memory storage
 */
@Singleton
public class TrackLocalDataSource implements TrackDataSource {
    /**
     * TODO with pagination
     * @param apiKey
     * @return
     */
    @Override
    public Observable<List<Track>> getTracks(int pages, String songName, String apiKey) {
        //TODO paginate
        return null;
    }

    @Override
    public Observable<List<Track>> getTracks(String songName, String apiKey) {
        return Observable
                .just(songName)
                .flatMap(params -> getTrackMap(params).map(TrackMap::getTrackList));
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

}
