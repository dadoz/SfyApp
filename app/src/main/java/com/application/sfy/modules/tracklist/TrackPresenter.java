package com.application.sfy.modules.tracklist;

import android.util.Log;
import android.util.SparseArray;

import com.application.sfy.data.TracksRepository;
import com.application.sfy.ui.ProgressLoader;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class TrackPresenter implements TrackContract.TrackPresenterInterface {
    private static final String TAG = "TrackPresenter";
    private static WeakReference<TrackContract.TrackView> trackView;
    private final TracksRepository repository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected ProgressLoader loader;

    @Inject
    TrackPresenter(TracksRepository repository) {
        this.repository = repository;
    }

    @Override
    public void unsubscribe() {
        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }

    /**
     * bind view to presenter
     * @param view
     */
    @Override
    public void bindView(TrackContract.TrackView view) {
        trackView = new WeakReference<>(view);
        loader = new ProgressLoader(
                view::showStandardLoading,
                view::hideStandardLoading);
    }

    /**
     * delete view
     */
    @Override
    public void deleteView() {
        trackView.clear();
    }

    /**
     * params
     * 0 -> songname
     *
     * retrieve item obs
     * @param params
     */
    @Override
    public void retrieveItems(SparseArray<Object> params) {
        Log.e(TAG, params.toString());

        //build obs
        compositeDisposable.add(repository
                .getTracks(params.get(0).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(composeLoaderTransformer(loader))
                .doOnError(Throwable::printStackTrace)
                .subscribe(
                        items -> trackView.get().onRenderData(items),
                        error -> trackView.get().onError(error.getMessage())));
    }


    /**
     * TODO mv to BASE
     * compose loader transformer
     * @param loader
     * @param <T>
     * @return
     */
    <T extends List>ObservableTransformer<T, T> composeLoaderTransformer(ProgressLoader loader) {
        return upstream -> upstream
                .doOnSubscribe(disposable -> loader.show.run())
                .doOnError(error -> loader.hide.run())
                .doOnNext(res -> loader.hide.run());
    }

    /**
     *
     * @return
     */
//    public SparseArray<Object> getParams() {
//        return params;
//    }

    /**
     *
     * @return
     */
    public SparseArray<Object> getMoreTracksParams(SparseArray<Object> params) {
        Integer[] pages = (Integer[]) params.get(0);
        params.setValueAt(0, new Integer[] {pages[pages.length -1] + 1});
        return params;
    }

    /**
     *
     * @return
     */
    public SparseArray<Object> getAllPagedParams(SparseArray<Object> params) {
        Integer[] pages = (Integer[]) params.get(0);
        Integer[] allPages = new Integer[pages[pages.length -1]];
        for (int i = 0; i < allPages.length; i++) {
            allPages[i] = i +1;
        }

        params.setValueAt(0, allPages);
        return params;
    }

    /**
     *
     */
    public void retrieveMoreItems(SparseArray<Object> params) {
        //set new pages
        params = getMoreTracksParams(params);
        //retrieve items
        retrieveItems(params);
    }


}