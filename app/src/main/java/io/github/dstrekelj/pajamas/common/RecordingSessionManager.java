package io.github.dstrekelj.pajamas.common;

import java.util.ArrayList;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.models.TrackModel;

/**
 * TODO: Comment.
 */
public class RecordingSessionManager {
    public static final String TAG = "RecordingSessionManager";

    private TrackModel track;

    private int numberOfCreatedStems;

    public RecordingSessionManager() {
        track = new TrackModel();
        track.setTitle("Untitled Track");
        track.setStems(new ArrayList<StemModel>());

        numberOfCreatedStems = 0;
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

    public StemModel createStem() {
        StemModel stem = new StemModel();
        stem.setTitle((numberOfCreatedStems == 0) ? "Untitled Stem" : "Untitled Stem #" + numberOfCreatedStems);
        track.getStems().add(stem);
        numberOfCreatedStems += 1;
        return stem;
    }

    public void deleteStem(StemModel stem) {
        track.getStems().remove(stem);
    }
}
