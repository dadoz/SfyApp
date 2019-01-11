package com.application.mxm.soundtracks.utils;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ImageView;

import com.application.mxm.soundtracks.R;
import com.bumptech.glide.Glide;

import java.io.InputStream;

public class Utils {
    /**
     * build params to get request
     * @param page
     * @param pageSize
     * @param country
     * @param fHasLyrics
     * @return
     */
    public static Bundle buildTrackParams(String country, String pageSize, String fHasLyrics, String page) {
        Bundle bundle = new Bundle();
        bundle.putString("PAGE", page);
        bundle.putString("PAGE_SIZE", pageSize);
        bundle.putString("COUNTRY", country);
        bundle.putString("FHASLYRICS", fHasLyrics);
        return bundle;
    }

    /**
     *
     * @param bundle
     * @return
     */
    public static SparseArray<Object> getTrackParamsFromBundle(Bundle bundle) {
        SparseArray<Object> array = new SparseArray<>();
        array.put(0, new Integer[] { Integer.parseInt(bundle.getString("PAGE", "1")) });
        array.put(1, bundle.getString("PAGE_SIZE", null));
        array.put(2, bundle.getString("COUNTRY", null));
        array.put(3, bundle.getString("FHASLYRICS", null));
        return array;
    }

    /**
     *
     *
     * @param artistName
     *@param trackName
     * @param trackId  @return
     */
    public static Bundle buildLyricsParams(String trackId, String artistName, String trackName, String avatarUrl) {
        Bundle bundle = new Bundle();
        bundle.putString("TRACK_ID", trackId);
        bundle.putString("ARTIST_NAME", artistName);
        bundle.putString("TRACK_NAME", trackName);
        bundle.putString("AVATAR_URL", avatarUrl);
        return bundle;
    }

    /**
     *
     * @param bundle
     * @return
     */
    public static SparseArray<String> getLyricsParamsFromBundle(Bundle bundle) {
        SparseArray<String> array = new SparseArray<>();
        array.put(0, bundle.getString("TRACK_ID", null));
        array.put(1, bundle.getString("ARTIST_NAME", null));
        array.put(2, bundle.getString("TRACK_NAME", null));
        array.put(3, bundle.getString("AVATAR_URL", null));
        return array;
    }

    /**
     * read file from assets, depending on filename provided
     * @param assets
     * @param filename
     * @return
     */
    public static String readFileFromAssets(AssetManager assets, String filename) {
        try {
            InputStream is = assets.open(filename);
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            return new String(buffer);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getTrackParamsKey(String page, String pageSize, String country, String fHasLyrics) {
        return page + "_" +pageSize + "_" + country + "_" + fHasLyrics;
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
                .placeholder(R.mipmap.github_placeholder)
                .into(avatarImageView);
    }
}
