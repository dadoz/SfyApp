package com.application.mxm.soundtracks.data.local;

import com.application.mxm.soundtracks.data.LyricsDataSource;
import com.application.mxm.soundtracks.data.model.Lyric;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

/**
 * In Ram memory storage
 */
@Singleton
public class LyricsLocalDataSource implements LyricsDataSource {

    @Override
    public boolean hasLyrics(String trackId) {
        try (Realm realm = Realm.getDefaultInstance()) {
            return realm.where(Lyric.class).equalTo("trackId", trackId)
                    .findFirst() != null;
        }
    }

    /**
     *
     * @param trackId
     * @param apiKey
     * @return
     */
    @Override
    public Observable<Lyric> getLyrics(String trackId, String apiKey) {
        return Observable.just(Realm.getDefaultInstance())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(realm -> realm
                        .where(Lyric.class).equalTo("trackId", trackId)
                        .findFirst()
                        .<Lyric>asFlowable()
                        .toObservable());
    }

    /**
     *
     * @param lyric
     * @param trackId
     */
    @Override
    public void setLyrics(Lyric lyric, String trackId) {
        Single.create((SingleOnSubscribe<Void>) singleSubscriber -> {
            try (Realm r = Realm.getDefaultInstance()) {
                r.executeTransaction(realm -> {
                    lyric.setTrackId(trackId);
                    realm.copyToRealm(lyric);
                });
            }})
                .subscribeOn(Schedulers.io())
                .subscribe();

    }
}
