package com.application.mxm.soundtracks.data.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by davide-syn on 4/27/18.
 */

public class TrackMap extends RealmObject {
    @PrimaryKey
    String trackParamsKey;

    RealmList<Track> trackList = new RealmList<>();

    public void setTrackList(RealmList<Track> trackList) {
        this.trackList = trackList;
    }

    public void setParamsKey(String paramsKey) {
        this.trackParamsKey = paramsKey;
    }

    public List<Track> getTrackList() {
        return trackList;
    }
}
