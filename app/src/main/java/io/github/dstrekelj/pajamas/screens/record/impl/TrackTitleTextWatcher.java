package io.github.dstrekelj.pajamas.screens.record.impl;

import android.text.Editable;
import android.text.TextWatcher;

import io.github.dstrekelj.pajamas.screens.record.RecordContract;

/**
 * An implementation of `TextWatcher` that updates the track title when the `EditText` view text is
 * changed.
 */
public class TrackTitleTextWatcher implements TextWatcher {
    private RecordContract.Presenter presenter;

    public TrackTitleTextWatcher(RecordContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        presenter.updateTrackTitle(editable.toString());
    }
}
