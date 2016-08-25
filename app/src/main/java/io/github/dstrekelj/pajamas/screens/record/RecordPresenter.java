package io.github.dstrekelj.pajamas.screens.record;

import io.github.dstrekelj.pajamas.data.PajamasDataRepository;
import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.models.TrackModel;
import io.github.dstrekelj.pajamas.recorder.RecordingSession;

/**
 * Record screen presenter, which handles the current recording session.
 */
public class RecordPresenter implements RecordContract.Presenter {
    public static final String TAG = "RecordPresenter";

    private PajamasDataRepository repository;
    private RecordContract.View view;
    private RecordingSession recordingSession;

    public RecordPresenter(RecordContract.View view, PajamasDataRepository repository) {
        this.view = view;
        this.repository = repository;

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
        return recordingSession.updateTrackPlayState();
    }

    @Override
    public void updateTrackTitle(String title) {
        recordingSession.setTrackTitle(title);
    }

    @Override
    public void finalizeTrack() {
        TrackModel track = recordingSession.finalizeTrack();
        repository.localDataSource.saveTrack(track);
    }
}
