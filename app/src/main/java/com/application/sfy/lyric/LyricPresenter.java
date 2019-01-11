package com.application.mxm.soundtracks.lyric;

import android.util.Log;
import android.util.SparseArray;

import com.application.mxm.soundtracks.data.LyricsRepository;
import com.application.mxm.soundtracks.data.model.Lyric;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;


public class LyricPresenter implements LyricContract.LyricsPresenterInterface {
    private static final String TAG = "TrackPresenter";
    private static WeakReference<LyricContract.LyricsView> wifiDeviceNetworkView;
    private final LyricsRepository repository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected ProgressLoader loader;
    private int limit = 0;
    private int N_ITEM_PAGE = 2;

    @Inject
    LyricPresenter(LyricsRepository repository) {
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
    public void bindView(LyricContract.LyricsView view) {
        wifiDeviceNetworkView = new WeakReference<>(view);
        loader = new ProgressLoader(
                view::showStandardLoading,
                view::hideStandardLoading);
    }

    /**
     * delete view
     */
    @Override
    public void deleteView() {
        wifiDeviceNetworkView.clear();
    }

    /**
     * retrieve item obs
     * @param params
     */
    @Override
    public void retrieveItems(SparseArray<String> params) {
        Log.e(TAG, params.toString());
        compositeDisposable.add(repository
                .getLyrics(params.get(0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(composeLoaderTransformer(loader))
                .doOnError(Throwable::printStackTrace)
                .subscribe(
                        items -> wifiDeviceNetworkView.get().onRenderData(items),
                        error ->wifiDeviceNetworkView.get().onError(error.getMessage())));
    }


    /**
     * compose loader transformer
     * @param loader
     * @param <T>
     * @return
     */
    <T extends Lyric>ObservableTransformer<T, T> composeLoaderTransformer(ProgressLoader loader) {
        return upstream -> upstream
                .doOnSubscribe(disposable -> loader.show.run())
                .doOnError(error -> loader.hide.run())
                .doOnNext(res -> loader.hide.run());
    }

    /**
     * progress loader
     */
    class ProgressLoader {
        Action show;
        Action hide;

        ProgressLoader(Action show, Action hide) {
            this.show = show;
            this.hide = hide;
        }
    }

}
