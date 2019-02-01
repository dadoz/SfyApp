package com.application.sfy.modules.tracklist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.sfy.R;
import com.application.sfy.data.model.Track;
import com.application.sfy.utils.Utils;

import java.util.List;


public class TrackListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Track> items;
    private OnTrackItemClickListener listener;
    private OnTrackLoadMoreClickListener listener2;
    private static final int VIEW_TYPE_FOOTER = 1;
    private static final int VIEW_TYPE_CELL = 0;

    public TrackListAdapter(List<Track> devices, OnTrackItemClickListener lst,
                            OnTrackLoadMoreClickListener lst2) {
        items = devices;
        listener = lst;
        listener2 = lst2;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //add Footer Button
        if (viewType == VIEW_TYPE_FOOTER) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.load_more_track_button, viewGroup, false);
            return new TrackListAdapter.RetrieveMoreTrackViewHolder(view);
        }

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.track_item, viewGroup, false);
        return new TrackListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            Track track = items.get(position);
            setAvatar((ViewHolder) viewHolder, track.getAlbumCoverart100x100());
            ((ViewHolder) viewHolder).trackNameTextview.setText(track.getTrackName());
            ((ViewHolder) viewHolder).artistNameTextview.setText(track.getArtistName());
            if (listener != null)
                ((ViewHolder) viewHolder).itemView.setOnClickListener(view -> listener.onTrackItemClick(view, items.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == items.size()) ? VIEW_TYPE_FOOTER : VIEW_TYPE_CELL;
    }
    /**
     * set avatar on user - Glide lib to handle pics
     * @param vh
     * @param avatarUrl
     */
    private void setAvatar(ViewHolder vh, String avatarUrl) {
        Utils.renderIcon(vh.avatarImageView, avatarUrl);
    }

    /**
     * add items from retrieve items
     * @param items
     */
    public void addItems(List<Track> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Track view holder
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView artistNameTextview;
        private final TextView trackNameTextview;
        private final ImageView avatarImageView;

        /**
         *
         * @param view
         */
        private ViewHolder(View view) {
            super(view);
            artistNameTextview =  view.findViewById(R.id.artistNameTextViewId);
            trackNameTextview =  view.findViewById(R.id.trackNameTextViewId);
            avatarImageView =  view.findViewById(R.id.avatarImageViewId);
        }


    }

    /**
     * T view holder
     */
    protected class RetrieveMoreTrackViewHolder extends RecyclerView.ViewHolder {
        /**
         *
         * @param view
         */
        private RetrieveMoreTrackViewHolder(View view) {
            super(view);
            view.findViewById(R.id.loadMoreTrackButtonId)
                    .setOnClickListener(v -> listener2.onTrackLoadMoreClick(v));
        }

    }

    /**
     * track item click cb
     */
    interface OnTrackItemClickListener {
        void onTrackItemClick(View view, Track item);
    }
    /**
     * track item click cb
     */
    interface OnTrackLoadMoreClickListener {
        void onTrackLoadMoreClick(View view);
    }

}
