package io.github.dstrekelj.pajamas.screens.record;

import android.icu.text.AlphabeticIndex;

import io.github.dstrekelj.pajamas.recorder.Recorder;
import io.github.dstrekelj.pajamas.recorder.RecordingSession;
import io.github.dstrekelj.pajamas.models.StemModel;

/**
 * TODO: Comment.
 */
public class RecordPresenter implements RecordContract.Presenter {
    public static final String TAG = "RecordPresenter";

    private RecordContract.View view;
    private RecordingSession sessionManager;

    public RecordPresenter(RecordContract.View view) {
        this.view = view;

        sessionManager = new RecordingSession();
    }

    @Override
    public void destroy() {
        sessionManager.destroy();
        sessionManager = null;
    }

    @Override
    public void start() {
        view.displayTrackTitle(sessionManager.getTrackTitle());
    }

    @Override
    public void stop() {
    }

    @Override
    public void createStem() {
        StemModel stem = sessionManager.createStem();
        view.displayStemInsertion(stem);
    }

    @Override
    public void deleteStem(StemModel stem) {
        sessionManager.deleteStem(stem);
        view.displayStemRemoval(stem);
    }

    @Override
    public void updateStemPlayPauseState(StemModel stem) {
    }

    private boolean isRecordingStem = false;
    @Override
    public void updateStemRecordState(StemModel stem) {
        if (isRecordingStem) {
            Recorder.stop(stem);
        } else {
            Recorder.record(stem);
        }
        isRecordingStem = !isRecordingStem;

    }

    @Override
    public void updateTrackPlayPauseState() {
    }

    @Override
    public void finalizeTrack() {
    }
}
