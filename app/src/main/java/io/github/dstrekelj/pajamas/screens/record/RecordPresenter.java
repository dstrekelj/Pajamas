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

    private HashMap<Integer, StemPlayer> stemPlayerMap;
    private HashMap<Integer, StemRecorder> stemRecorderMap;

    public RecordPresenter(RecordContract.View view) {
        this.view = view;

        recordingSession = new RecordingSession();
        stemPlayerMap = new HashMap<>();
        stemRecorderMap = new HashMap<>();
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
        if (stemPlayerMap.containsKey(stem.getId())) {
            stemPlayerMap.get(stem.getId()).stop();
            stemPlayerMap.remove(stem.getId());
        } else {
            stemPlayerMap.put(stem.getId(), StemPlayerFactory.getStemPlayer(stem));
        }
    }

    @Override
    public void updateStemRecordState(StemModel stem) {
        if (stemRecorderMap.containsKey(stem.getId())) {
            stemRecorderMap.get(stem.getId()).stop();
            stemRecorderMap.remove(stem.getId());
        } else {
            stemRecorderMap.put(stem.getId(), StemRecorderFactory.getStemRecorder(stem));
        }
    }

    @Override
    public void updateTrackPlayPauseState() {
    }

    @Override
    public void finalizeTrack() {
    }
}
