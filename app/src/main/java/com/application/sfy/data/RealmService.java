package com.application.sfy.data;

import io.realm.Realm;

/**
 * Created by davide-syn on 4/27/18.
 */

public class RealmService {

    private final Realm mRealm;

    public RealmService(final Realm realm) {
        mRealm = realm;
    }

    public void closeRealm() {
        mRealm.close();
    }

    //other methods
}