package io.github.dstrekelj.pajamas.screens.record;

import io.github.dstrekelj.pajamas.R;
import io.github.dstrekelj.pajamas.data.PajamasDataRepository;
import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.recorder.RecordingSession;

/**
 * Record screen presenter, which handles the current recording session.
 */
public class RecordPresenter implements RecordContract.Presenter {
    public static final String TAG = "RecordPresenter";

    private PajamasDataRepository repository;
    private RecordContract.View view;
    private RecordingSession recordingSession;

    public RecordPresenter(RecordContract.View view, PajamasDataRepository repository, String defaultTrackTitle, String defaultStemTitle) {
        this.view = view;
        this.repository = repository;

        recordingSession = new RecordingSession(defaultTrackTitle, defaultStemTitle);
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
        StemModel stem = recordingSession.addStem();
        view.displayStemInsertion(stem);
    }

    @Override
    public void deleteStem(StemModel stem) {
        recordingSession.removeStem(stem);
        view.displayStemRemoval(stem);
    }

    @Override
    public int updateStemPlayerState(StemModel stem) {
        switch (recordingSession.getStemPlayerState(stem)) {
            case RecordingSession.STATE_ACTIVE:
                recordingSession.stopStemPlayback(stem);
                break;
            case RecordingSession.STATE_INACTIVE:
                recordingSession.startStemPlayback(stem);
                break;
            case RecordingSession.STATE_UNAVAILABLE:
                view.displayAlertDialog(R.string.dialog_title_failure, R.string.error_audio_player_unavailable);
                break;
        }

        return recordingSession.getStemPlayerState(stem);
    }

    @Override
    public int updateStemRecorderState(StemModel stem) {
        switch (recordingSession.getStemRecorderState(stem)) {
            case RecordingSession.STATE_ACTIVE:
                updateTrackPlayerState();
                recordingSession.stopStemRecording(stem);
                break;
            case RecordingSession.STATE_INACTIVE:
                view.displayProgressDialog(R.string.dialog_title_please_wait, R.string.finalization_in_progress);
                recordingSession.previewTrack(stem);
                view.dismissProgressDialog();
                updateTrackPlayerState();
                recordingSession.startStemRecording(stem);
                break;
            case RecordingSession.STATE_UNAVAILABLE:
                view.displayAlertDialog(R.string.dialog_title_failure, R.string.error_audio_recorder_unavailable);
                break;
        }

        return recordingSession.getStemRecorderState(stem);
    }

    @Override
    public int updateTrackPlayerState() {
        switch (recordingSession.getTrackPlayerState()) {
            case RecordingSession.STATE_ACTIVE:
                recordingSession.stopTrackPlayback();
                break;
            case RecordingSession.STATE_INACTIVE:
                view.displayProgressDialog(R.string.dialog_title_please_wait, R.string.finalization_in_progress);
                recordingSession.finalizeTrack();
                view.dismissProgressDialog();
                if (recordingSession.isAudioReady(recordingSession.getTrack())) {
                    recordingSession.startTrackPlayback();
                    break;
                }
            case RecordingSession.STATE_UNAVAILABLE:
                view.displayAlertDialog(R.string.dialog_title_failure, R.string.error_audio_player_unavailable);
                break;
        }

        return recordingSession.getTrackPlayerState();
    }

    @Override
    public void updateTrackTitle(String title) {
        recordingSession.setTrackTitle(title);
    }

    @Override
    public void finalizeTrack() {
        view.displayProgressDialog(R.string.dialog_title_please_wait, R.string.finalization_in_progress);
        recordingSession.finalizeTrack();
        view.dismissProgressDialog();
        repository.localDataSource.saveTrack(recordingSession.getTrack());
        view.displayAlertDialog(R.string.dialog_title_success, R.string.finalization_successful);
    }
}
