package com.application.sfy.data;

import com.application.sfy.data.local.Local;
import com.application.sfy.data.local.LyricsLocalDataSource;
import com.application.sfy.data.remote.LyricsNetworkDataSource;
import com.application.sfy.data.remote.Remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LyricsRepositoryModule {

    @Provides
    @Singleton
    @Local
    LyricsDataSource provideLyricsLocalDataSource() {
        return new LyricsLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    LyricsDataSource provideLyricsRemoteDataSource() {
        return new LyricsNetworkDataSource();
    }
}
