package com.application.sfy.ui;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.application.sfy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackInputDataView extends RelativeLayout {
    private static final String DEFAULT_COUNTRY = "US";

    @BindView(R.id.findButtonId)
    View findButton;
    @BindView(R.id.songNameTextInputLayoutId)
    TextInputLayout songNameTextInputLayout;
    private String songName;


    public TrackInputDataView(Context context) {
        super(context);
        initView();
    }

    public TrackInputDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TrackInputDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.track_input_textinput_layout, this);
        ButterKnife.bind(this);
        songNameTextInputLayout.getEditText().addTextChangedListener(new TextWatcherImpl("pageSize"));
    }

    public void setFindButtonOnClickListener(OnClickListener listener) {
        findButton.setOnClickListener(listener);
    }

    public String getpageSize() {
        return songNameTextInputLayout.getEditText().getText().toString();
    }




    /**
     * 
     * @return
     */
    public boolean isValidInputData() {
        return !songNameTextInputLayout.getEditText().getText().toString().isEmpty();
    }

    /**
     * 
     */
    public void setErrorInputData() {
        if (songNameTextInputLayout.getEditText().getText().toString().isEmpty())
            songNameTextInputLayout.setError(getContext().getString(R.string.no_input_data));
    }

    /**
     * custom imple of text watcher
     */
    private class TextWatcherImpl implements TextWatcher {
        private final String type;

        TextWatcherImpl(String type) {
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence text, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (type) {
                case "pageSize":
                    songName = songNameTextInputLayout.getEditText().getText().toString();
                    if (songName.isEmpty()) {
                        songNameTextInputLayout.setError(getContext().getString(R.string.max_default));
                        return;
                    }
                    //hide error
                    songNameTextInputLayout.setError(null);
                    break;
            }
        }
    }
}
