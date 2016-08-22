package io.github.dstrekelj.pajamas.screens.record;

import java.util.HashMap;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.recorder.RecordingSession;
import io.github.dstrekelj.pajamas.recorder.StemPlayer;
import io.github.dstrekelj.pajamas.recorder.StemPlayerFactory;
import io.github.dstrekelj.pajamas.recorder.StemRecorder;
import io.github.dstrekelj.pajamas.recorder.StemRecorderFactory;

/**
 * TODO: Comment.
 */
public class RecordPresenter implements RecordContract.Presenter {
    public static final String TAG = "RecordPresenter";

    private RecordContract.View view;
    private RecordingSession recordingSession;

    public RecordPresenter(RecordContract.View view) {
        this.view = view;

        recordingSession = new RecordingSession();
    }

    @Override
    public void destroy() {
        recordingSession.destroy();
        recordingSession = null;
    }

    @Override
    public void start() {
        view.displayTrackTitle(recordingSession.getTrackTitle());
    }

    @Override
    public void stop() {
        // TODO: 21.8.2016. Stop currently active recording or playback
    }

    @Override
    public void createStem() {
        StemModel stem = recordingSession.createStem();
        view.displayStemInsertion(stem);
    }

    @Override
    public void deleteStem(StemModel stem) {
        recordingSession.deleteStem(stem);
        view.displayStemRemoval(stem);
    }

    @Override
    public int updateStemPlayState(StemModel stem) {
        return recordingSession.updateStemPlayState(stem);
    }

    @Override
    public int updateStemRecordState(StemModel stem) {
        return recordingSession.updateStemRecordState(stem);
    }

    @Override
    public int updateTrackPlayState() {
        return 0;
    }

    @Override
    public void finalizeTrack() {
    }
}
