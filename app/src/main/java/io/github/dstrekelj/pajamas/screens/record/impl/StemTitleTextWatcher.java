package io.github.dstrekelj.pajamas.screens.record.impl;

import android.text.Editable;
import android.text.TextWatcher;

import io.github.dstrekelj.pajamas.models.StemModel;

/**
 * TODO: Comment.
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
