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
    public void retrieveItems(SparseArray<String> params) {
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

}