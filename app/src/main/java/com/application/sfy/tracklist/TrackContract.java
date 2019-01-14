package com.application.sfy.tracklist;

import android.util.SparseArray;

import com.application.sfy.BasePresenter;
import com.application.sfy.data.model.Track;

import java.util.List;

public interface TrackContract {

    interface TrackView {
        void onRenderData(List<Track> items);
        void onError(String error);
        void showStandardLoading();
        void hideStandardLoading();
    }
    interface TrackPresenterInterface extends BasePresenter {
        void unsubscribe();
        void retrieveItems(SparseArray<Object> params);
        void bindView(TrackView view);
        void deleteView();
    }
}