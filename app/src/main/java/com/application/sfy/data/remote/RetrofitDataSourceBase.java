package com.application.sfy.data.remote;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * Created by davide-syn on 4/26/18.
 */
public class RetrofitDataSourceBase {
    protected <T> ObservableTransformer<T, T> handleRxErrorsTransformer() {
        return upstream -> upstream
                .onErrorResumeNext(error -> {
                    //else generic no net error
                    return Observable.error(new Throwable("Generic exception", error));
                });
    }
}
