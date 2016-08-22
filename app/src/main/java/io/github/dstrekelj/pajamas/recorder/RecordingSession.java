package io.github.dstrekelj.pajamas.recorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.models.TrackModel;

/**
 * TODO: Comment.
 */
public class RecordingSession {
    public static final String TAG = "RecordingSession";

    public static final int STEM_PLAYER_ACTIVE = 0;
    public static final int STEM_PLAYER_STOPPED = 1;

    public static final int STEM_RECORDER_ACTIVE = 2;
    public static final int STEM_RECORDER_BUSY = 3;
    public static final int STEM_RECORDER_STOPPED = 4;

    public static final int TRACK_PLAYER_ACTIVE = 5;
    public static final int TRACK_PLAYER_STOPPED = 6;

    private TrackModel track;

    private HashMap<Integer, StemPlayer> stemPlayers;
    private StemRecorder stemRecorder;

    private int numberOfCreatedStems;

    public RecordingSession() {
        track = new TrackModel();
        track.setTitle("Untitled Track");
        track.setStems(new ArrayList<StemModel>());

        numberOfCreatedStems = 0;

        stemPlayers = new HashMap<>();
    }

    public void destroy() {
        track = null;
    }

    public String getTrackTitle() {
        return track.getTitle();
    }

    public void setTrackTitle(String title) {
        track.setTitle(title);
    }

    public List<StemModel> getTrackStems() {
        return track.getStems();
    }

    public StemModel createStem() {
        StemModel stem = new StemModel();
        stem.setTitle((numberOfCreatedStems == 0) ? "Untitled Stem" : "Untitled Stem #" + numberOfCreatedStems);
        stem.setId(numberOfCreatedStems);
        track.getStems().add(stem);
        numberOfCreatedStems += 1;
        return stem;
    }

    public void deleteStem(StemModel stem) {
        track.getStems().remove(stem);
    }

    public int updateStemPlayState(StemModel stem) {
        if (isPlayingStem(stem)) {
            stemPlayers.get(stem.getId()).stop();
            stemPlayers.remove(stem.getId());
            return STEM_PLAYER_STOPPED;
        } else {
            stemPlayers.put(stem.getId(), StemPlayerFactory.getStemPlayer(stem));
            return STEM_PLAYER_ACTIVE;
        }
    }

    public int updateStemRecordState(StemModel stem) {
        if (isRecordingStem(stem)) {
            stemRecorder.stop();
            stemRecorder = null;
            return STEM_RECORDER_STOPPED;
        } else {
            if (stemRecorder == null) {
                stemRecorder = StemRecorderFactory.getStemRecorder(stem);
                return STEM_RECORDER_ACTIVE;
            }
        }
        return STEM_RECORDER_BUSY;
    }

    public boolean isPlayingStem(StemModel stem) {
        return stemPlayers.containsKey(stem.getId());
    }

    public boolean isRecordingStem(StemModel stem) {
        return (stemRecorder != null) && (stemRecorder.getStem().getId() == stem.getId());
    }
}
