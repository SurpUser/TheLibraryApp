package com.fcprograms.root.thelibraryapp.Tools;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * Created by AnthonyM on 6/15/2016.
 */
public abstract class TextValidador implements TextWatcher {
    private final TextView textView;

    public TextValidador(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}