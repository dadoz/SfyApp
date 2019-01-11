package com.application.mxm.soundtracks.data.remote;

import com.application.mxm.soundtracks.data.LyricsDataSource;
import com.application.mxm.soundtracks.data.model.Lyric;
import com.application.mxm.soundtracks.data.remote.services.RetrofitServiceRx;

import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by davide-syn on 4/24/18.
 */

@Singleton
public class LyricsNetworkDataSource extends RetrofitDataSourceBase implements LyricsDataSource {
//    public Observable<Lyric> getLyrics(Context context, String owner, String repo) {
//        try {
//            InputStream inputStream = context.getAssets().open("sound_track_lyrics_response_200.json");
//            int size = inputStream.available();
//            byte[] buffer = new byte[size];
//            inputStream.read(buffer);
//            inputStream.close();
//            String json = new String(buffer, "UTF-8");
//
//            Lyric res = new GsonBuilder()
//                    .registerTypeAdapter(Lyric.class, new RetrofitServiceRx.LyricsJsonDeserializer())
//                    .create()
//                    .fromJson(json, Lyric.class);
//            return Observable.just(res);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new UnsupportedOperationException("error on get data");
//        }
//    }

    /**
     *
     * @param trackId
     * @param apiKey
     * @return
     */
    public Observable<Lyric> getLyrics(String trackId, String apiKey) {
        return new RetrofitServiceRx().getSoundtrackRetrofit()
                .getLyrics(trackId, apiKey)
                .compose(handleRxErrorsTransformer());
    }

    /**
     * TODO plese refactorize
     */
    @Override
    public void setLyrics(Lyric lyric, String trackId) {
    }

    @Override
    public boolean hasLyrics(String trackId) {
        return false;
    }
}
