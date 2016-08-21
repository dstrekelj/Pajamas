package io.github.dstrekelj.pajamas.screens.record;

import io.github.dstrekelj.pajamas.recorder.Recorder;
import io.github.dstrekelj.pajamas.recorder.RecordingSession;
import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.recorder.StemRecorder;

/**
 * TODO: Comment.
 */
public class RecordPresenter implements RecordContract.Presenter {
    public static final String TAG = "RecordPresenter";

    private RecordContract.View view;
    private RecordingSession recordingSession;

    private StemRecorder stemRecorder;

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
    public void updateStemPlayPauseState(StemModel stem) {
    }

    private boolean isRecordingStem = false;
    @Override
    public void updateStemRecordState(StemModel stem) {
        if (isRecordingStem) {
            if (stemRecorder != null) stemRecorder.stop();
        } else {
            stemRecorder = Recorder.record(stem);
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
