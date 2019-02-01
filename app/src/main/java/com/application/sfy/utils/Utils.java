package com.application.sfy.utils;

import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ImageView;

import com.application.sfy.R;
import com.bumptech.glide.Glide;

public class Utils {

    private static final String TRACK_NAME = "TRACK_NAME";

    /**
     * build params to get request
     * @param trackName
     * @return
     */
    public static Bundle buildTrackParams(String trackName) {
        Bundle bundle = new Bundle();
        bundle.putString(TRACK_NAME, trackName);
        return bundle;
    }

    /**
     *
     * @param bundle
     * @return
     */
    public static SparseArray<String> getTrackParamsFromBundle(Bundle bundle) {
        SparseArray<String> array = new SparseArray<>();
        array.put(0, bundle.getString(TRACK_NAME, null));
        return array;
    }

    /**
     *
     * @param params
     */
    public static Bundle putTrackParamsFromBundle(SparseArray<String> params) {
        String trackName = params.get(0);
        return Utils.buildTrackParams(trackName);
    }

    /**
     *
     * @param avatarImageView
     * @param avatarUrl
     */
    public static void renderIcon(ImageView avatarImageView, String avatarUrl) {
        if (avatarUrl == null) {
            Glide.clear(avatarImageView);
            return;
        }

        Glide.with(avatarImageView.getContext())
                .load(avatarUrl)
                .placeholder(R.mipmap.noicon_placeholder)
                .into(avatarImageView);
    }
}
