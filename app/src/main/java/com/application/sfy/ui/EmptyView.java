package com.application.sfy.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.application.sfy.R;

public class EmptyView extends LinearLayout {

    public EmptyView(Context context) {
        super(context);
        initView();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.empty_view_layout, this);
    }

}
