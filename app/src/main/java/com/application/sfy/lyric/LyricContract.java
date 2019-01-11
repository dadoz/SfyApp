package com.application.mxm.soundtracks.lyric;

import android.util.SparseArray;

import com.application.mxm.soundtracks.BasePresenter;
import com.application.mxm.soundtracks.data.model.Lyric;

public interface LyricContract {

    interface LyricsView {
        void onRenderData(Lyric item);
        void onError(String error);
        void showStandardLoading();
        void hideStandardLoading();
    }
    interface LyricsPresenterInterface extends BasePresenter {
        void unsubscribe();
        void retrieveItems(SparseArray<String> params);
        void bindView(LyricsView view);
        void deleteView();
    }
}