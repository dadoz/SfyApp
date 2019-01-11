package com.application.mxm.soundtracks.di;

import android.app.Application;

import com.application.mxm.soundtracks.SoundtrackApplication;
import com.application.mxm.soundtracks.data.LyricsRepositoryModule;
import com.application.mxm.soundtracks.data.TracksRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        LyricsRepositoryModule.class,
        TracksRepositoryModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<SoundtrackApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);
        AppComponent build();
    }
}
