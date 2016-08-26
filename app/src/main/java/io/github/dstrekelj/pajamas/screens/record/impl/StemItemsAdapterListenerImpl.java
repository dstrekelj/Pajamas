package io.github.dstrekelj.pajamas.screens.record.impl;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.screens.record.RecordContract;
import io.github.dstrekelj.pajamas.screens.record.adapters.StemItemsAdapter;
import io.github.dstrekelj.pajamas.screens.record.views.StemView;

/**
 * An implementation of the stem items adapter listener.
 */
public class StemItemsAdapterListenerImpl implements StemItemsAdapter.StemItemsAdapterListener {
    private RecordContract.Presenter presenter;
    private RecordContract.View view;

    public StemItemsAdapterListenerImpl(RecordContract.View view, RecordContract.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
    }

    @Override
    public void onStemRecord(StemModel stem, StemView stemView) {
        int state = presenter.updateStemRecorderState(stem);
        stemView.setStemRecordState(state);
    }

    @Override
    public void onStemPlay(StemModel stem, StemView stemView) {
        int state = presenter.updateStemPlayerState(stem);
        stemView.setStemPlayState(state);
    }

    @Override
    public void onStemRemove(StemModel stem, StemView stemView) {
        presenter.deleteStem(stem);
        stemView.invalidate();
    }
}
