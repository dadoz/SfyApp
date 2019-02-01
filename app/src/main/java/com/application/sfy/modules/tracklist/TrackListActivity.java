package com.application.sfy.modules.tracklist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.application.sfy.R;
import com.application.sfy.data.model.Track;
import com.application.sfy.ui.EmptyView;
import com.application.sfy.utils.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

import static com.application.sfy.MainActivity.TRACK_PARAMS_KEY;

/**
 * stargazer activity
 */
public class TrackListActivity extends DaggerAppCompatActivity implements TrackContract.TrackView,
        TrackListAdapter.OnTrackItemClickListener, TrackListAdapter.OnTrackLoadMoreClickListener {
    @BindView(R.id.trackRecyclerViewId)
    RecyclerView recyclerView;
    @BindView(R.id.trackProgressbarId)
    ProgressBar progressBar;
    @BindView(R.id.emptyViewId)
    EmptyView emptyView;
    @Inject
    TrackPresenter presenter;

    private Unbinder unbinder;
    private SparseArray<String> params;
    private static final String TRACK_PARAMS_BUNDLE = "TRACK_PARAMS_BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);
        unbinder = ButterKnife.bind(this);

        //saving params in instanceState
        setParams(savedInstanceState);
        onInitView();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unsubscribe();
            presenter.deleteView();
        }

        if (unbinder != null)
            unbinder.unbind();
    }

    /**
     * iit view and retrieve stargazers data
     */
    private void onInitView() {
        initActionbar();
        presenter.bindView(this);
        presenter.retrieveItems(params);
    }

    /**
     *
     * @return
     * @param savedInstanceState
     */
    private void setParams(Bundle savedInstanceState) {
        //get params from savedInstance
        //get params from intent
        params = savedInstanceState != null ?
                Utils.getTrackParamsFromBundle(savedInstanceState.getBundle(TRACK_PARAMS_BUNDLE)) :
                Utils.getTrackParamsFromBundle(getIntent().getExtras().getBundle(TRACK_PARAMS_KEY));
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
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRenderData(List<Track> items) {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        initRecyclerView(items);
    }


    @Override
    public void onError(String error) {
        recyclerView.setVisibility(View.GONE);
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
     * init recycler view binding data by adapter
     * @param items
     */
    private void initRecyclerView(List<Track> items) {
        if (items.size() == 0) {
            return;
        }
        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new TrackListAdapter(items, this, this));
    }

    @Override
    public void onTrackItemClick(View view, Track track) {
        //TODO implement
        throw new UnsupportedOperationException("method ot impl");
    }

    @Override
    public void onTrackLoadMoreClick(View view) {
        //TODO implement
        throw new UnsupportedOperationException("method ot impl");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Bundle bundle = Utils.putTrackParamsFromBundle(params);
        savedInstanceState.putBundle(TRACK_PARAMS_BUNDLE, bundle);
    }

    /**
     *
     * @param context
     * @return
     */
    public static Intent buildIntent(Context context, String trackName) {
        Bundle bundle = Utils.buildTrackParams(trackName);
        Intent intent = new Intent(context, TrackListActivity.class);
        intent.putExtra(TRACK_PARAMS_KEY, bundle);
        return intent;
    }
}
