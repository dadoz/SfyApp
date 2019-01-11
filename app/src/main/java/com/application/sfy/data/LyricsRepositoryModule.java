package com.application.mxm.soundtracks.data;

import com.application.mxm.soundtracks.data.local.Local;
import com.application.mxm.soundtracks.data.local.LyricsLocalDataSource;
import com.application.mxm.soundtracks.data.remote.LyricsNetworkDataSource;
import com.application.mxm.soundtracks.data.remote.Remote;

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
