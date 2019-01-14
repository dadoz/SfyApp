package com.application.sfy.lyric;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.application.sfy.R;
import com.application.sfy.data.model.Lyric;
import com.application.sfy.ui.EmptyView;
import com.application.sfy.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

import static com.application.sfy.tracklist.TrackListActivity.LYRICS_PARAMS_KEY;

/**
 * stargazer activity
 */
public class LyricActivity extends DaggerAppCompatActivity implements LyricContract.LyricsView {
    @BindView(R.id.artistNameTextViewId)
    TextView artistNameTextView;
    @BindView(R.id.trackNameTextViewId)
    TextView trackNameTextView;
    @BindView(R.id.avatarImageViewId)
    ImageView avatarImageView;
    @BindView(R.id.lyricsTextViewId)
    TextView lyricsTextView;

    @BindView(R.id.lyricsProgressbarId)
    ProgressBar progressBar;
    @BindView(R.id.lyricsEmptyViewId)
    EmptyView emptyView;

    @Inject
    LyricPresenter presenter;

    private Unbinder unbinder;
    private SparseArray<String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);
        unbinder = ButterKnife.bind(this);
        onInitView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

    /**
     * iit view and retrieve stargazers data
     */
    private void onInitView() {
        initActionbar();
        params = Utils.getLyricsParamsFromBundle(getIntent().getExtras().getBundle(LYRICS_PARAMS_KEY));
        presenter.bindView(this);
        presenter.retrieveItems(params);
    }

    /**
     * TODO mv to base activity
     * actionbar set listener and back arrow
     */
    private void initActionbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * todo mv on base
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (presenter != null)
                    presenter.unsubscribe();
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRenderData(Lyric lyric) {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        artistNameTextView.setText(params.get(1));
        trackNameTextView.setText(params.get(2));
        Utils.renderIcon(avatarImageView, params.get(3));
        lyricsTextView.setText(lyric.getLyricsBody());
    }


    @Override
    public void onError(String error) {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        Snackbar.make(findViewById(R.id.activity_main), R.string.retrieve_error,
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showStandardLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideStandardLoading() {
        progressBar.setVisibility(View.GONE);
    }

    /**
     * build intent
     * @param context
     * @param trackId
     * @return
     */
    public static Intent buildIntent(Context context, Integer trackId, String artistName, String trackName, String avatarUrl) {
        Bundle bundle = Utils.buildLyricsParams(Integer.toString(trackId), artistName, trackName, avatarUrl);
        Intent intent = new Intent(context, LyricActivity.class);
        intent.putExtra(LYRICS_PARAMS_KEY, bundle);
        return intent;
    }
}
