package io.github.dstrekelj.pajamas.screens.record.impl;

import android.text.Editable;
import android.text.TextWatcher;

import io.github.dstrekelj.pajamas.models.StemModel;

/**
 * An implementation of `TextWatcher` that updates the track stem title when the `EditText` view
 * text is changed.
 */
public class StemTitleTextWatcher implements TextWatcher {
    private StemModel stem;

    public StemTitleTextWatcher(StemModel stem) {
        this.stem = stem;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        stem.setTitle(editable.toString());
    }
}
