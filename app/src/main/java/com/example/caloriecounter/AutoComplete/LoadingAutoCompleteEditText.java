package com.example.caloriecounter.AutoComplete;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class LoadingAutoCompleteEditText extends AppCompatAutoCompleteTextView {
    public LoadingAutoCompleteEditText(Context context) {
        super(context);
    }

    private ProgressBar mLoadingIndicator;

    public void setLoadingIndicator(ProgressBar progressBar) {
        mLoadingIndicator = progressBar;
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        if (mLoadingIndicator != null) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFilterComplete(int count) {
        if (mLoadingIndicator != null) {
            mLoadingIndicator.setVisibility(View.GONE);
        }
        super.onFilterComplete(count);
    }
}
