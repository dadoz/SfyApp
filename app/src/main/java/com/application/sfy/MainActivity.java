package com.application.sfy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.application.sfy.modules.tracklist.TrackListActivity;
import com.application.sfy.ui.TrackInputDataView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TRACK_PARAMS_KEY = "TRACK_PARAMS_KEY";

    @BindView(R.id.trackInputDataViewId)
    TrackInputDataView trackInputDataView;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
     * init view to handle button in custom view interaction
     */
    private void onInitView() {
        trackInputDataView.setFindButtonOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!trackInputDataView.isValidInputData()) {
            trackInputDataView.setErrorInputData();
            return;
        }

        Intent intent = TrackListActivity.buildIntent(this,
                trackInputDataView.getTrackName());
        startActivity(intent);
    }
}
