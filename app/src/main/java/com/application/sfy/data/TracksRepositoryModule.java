package com.application.sfy.data;

import com.application.sfy.data.local.Local;
import com.application.sfy.data.local.TrackLocalDataSource;
import com.application.sfy.data.remote.Remote;
import com.application.sfy.data.remote.TrackNetworkDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class TracksRepositoryModule {
    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    @Local
    TrackDataSource provideStargazerLocalDataSource() {
        return new TrackLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    TrackDataSource provideStargazerRemoteDataSource() {
        return new TrackNetworkDataSource();
    }

}
