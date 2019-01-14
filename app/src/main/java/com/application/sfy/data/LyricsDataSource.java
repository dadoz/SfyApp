package com.application.sfy.data;


import com.application.sfy.data.model.Lyric;

import io.reactivex.Observable;

public interface LyricsDataSource {
    Observable<Lyric> getLyrics(String trackId, String apiKey);
//    Observable<Lyric> getLyrics(Context context, String owner, String repo);
    void setLyrics(Lyric lyrics, String paramKey);
    boolean hasLyrics(String paramKey);
}
