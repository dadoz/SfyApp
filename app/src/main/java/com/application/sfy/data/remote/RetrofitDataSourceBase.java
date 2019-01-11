package com.application.mxm.soundtracks.data.remote;

import com.application.mxm.soundtracks.data.exceptions.ApiException;
import com.application.mxm.soundtracks.data.exceptions.NetworkException;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import retrofit2.HttpException;

/**
 * Created by davide-syn on 4/26/18.
 */
public class RetrofitDataSourceBase {
    protected <T> ObservableTransformer<T, T> handleRxErrorsTransformer() {
        return upstream -> upstream
                .onErrorResumeNext(error -> {
                    if (error instanceof HttpException) {
                        HttpException httpException = (HttpException) error;
                        String body = "";
                        if (httpException.response() != null
                                && httpException.response().errorBody() != null
                                && httpException.response().errorBody().string() != null) {
                            body = httpException.response().errorBody().string();
                        }

                        return Observable.error(ApiException.create(
                                httpException.message(),
                                httpException,
                                httpException.code(),
                                body));
                    }

                    //else generic no net error
                    return Observable.error(NetworkException.networkError(error));
                });
    }
}
