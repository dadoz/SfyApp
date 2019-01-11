package com.application.mxm.soundtracks.ui;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.application.mxm.soundtracks.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrackInputDataView extends RelativeLayout {
    private static final String DEFAULT_COUNTRY = "US";

    @BindView(R.id.findButtonId)
    Button findButton;
    @BindView(R.id.pageSizeTextInputLayoutId)
    TextInputLayout pageSizeTextInputLayout;
    @BindView(R.id.countryTextInputLayoutId)
    TextInputLayout countryTextInputLayout;
    @BindView(R.id.hasLyricsCheckboxId)
    CheckBox hasLyricsCheckbox;
//    @State
    String country;
//    @State
    String pageSize;


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
        pageSizeTextInputLayout.getEditText().addTextChangedListener(new TextWatcherImpl("pageSize"));
        countryTextInputLayout.getEditText().addTextChangedListener(new TextWatcherImpl("country"));
    }

    public void setFindButtonOnClickListener(OnClickListener listener) {
        findButton.setOnClickListener(listener);
    }

    public String getpageSize() {
        return pageSizeTextInputLayout.getEditText().getText().toString();
    }

    public String getcountry() {
        String value = countryTextInputLayout.getEditText().getText().toString();
        if (value.isEmpty())
            return DEFAULT_COUNTRY;
        return value;
    }

    /**
     * 
     * @return
     */
    public boolean hasLyricsCheckbox() {
        return hasLyricsCheckbox.isChecked();
    }

    /**
     * 
     * @return
     */
    public boolean isValidInputData() {
        return !countryTextInputLayout.getEditText().getText().toString().isEmpty() &&
                !pageSizeTextInputLayout.getEditText().getText().toString().isEmpty() &&
                Integer.parseInt(pageSizeTextInputLayout.getEditText().getText().toString()) <= 100;
    }

    /**
     * 
     */
    public void setErrorInputData() {
        if (countryTextInputLayout.getEditText().getText().toString().isEmpty())
            countryTextInputLayout.setError(getContext().getString(R.string.no_input_data));

        if (pageSizeTextInputLayout.getEditText().getText().toString().isEmpty())
            pageSizeTextInputLayout.setError(getContext().getString(R.string.no_input_data));
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
                case "country":
                    country = countryTextInputLayout.getEditText().getText().toString();
                    countryTextInputLayout.setError(null);
                    break;
                case "pageSize":
                    pageSize = pageSizeTextInputLayout.getEditText().getText().toString();
                    if (!pageSize.isEmpty() &&
                            Integer.parseInt(pageSize.toString()) > 100) {
                        pageSizeTextInputLayout.setError(getContext().getString(R.string.max_default));
                        return;
                    }
                    pageSizeTextInputLayout.setError(null);
                    break;
            }
        }
    }
}
