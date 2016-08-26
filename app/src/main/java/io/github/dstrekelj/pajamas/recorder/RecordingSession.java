package io.github.dstrekelj.pajamas.recorder;

import android.util.Log;

import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.models.TrackModel;

/**
 * A recording session handles the current track and track stems, their recording, and playback.
 */
public class RecordingSession {
    public static final String TAG = "RecordingSession";

    public static final int STATE_PLAYER_ACTIVE = 0;
    public static final int STATE_PLAYER_STOPPED = 1;
    public static final int STATE_PLAYER_UNAVAILABLE = 2;
    public static final int STATE_RECORDER_ACTIVE = 3;
    public static final int STATE_RECORDER_STOPPED = 4;
    public static final int STATE_RECORDER_UNAVAILABLE = 5;

    private static final int ID_TRACK = -1;

    private HashMap<Integer, AudioPlayer> audioPlayers;
    private HashMap<Integer, AudioRecorder> audioRecorders;
    private TrackModel track;

    private final String defaultStemTitle;
    private int numberOfCreatedStems;

    public RecordingSession(String defaultTrackTitle, String defaultStemTitle) {
        audioPlayers = new HashMap<>();
        audioRecorders = new HashMap<>();

        track = new TrackModel();
        track.setStems(new ArrayList<StemModel>());
        track.setTitle(defaultTrackTitle);

        this.defaultStemTitle = defaultStemTitle;
        numberOfCreatedStems = 0;
    }

    public void destroy() {
        audioPlayers.clear();
        audioPlayers = null;
        audioRecorders.clear();
        audioRecorders = null;
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

    public StemModel addStem() {
        StemModel stem = new StemModel();
        stem.setId(numberOfCreatedStems);
        stem.setTitle((numberOfCreatedStems == 0) ? defaultStemTitle : defaultStemTitle + " #" + numberOfCreatedStems);

        track.getStems().add(stem);

        numberOfCreatedStems += 1;

        return stem;
    }

    public void removeStem(StemModel stem) {
        track.getStems().remove(stem);
        if (audioPlayers.containsKey(stem.getId())) {
            audioPlayers.remove(stem.getId());
        }
        if (audioRecorders.containsKey(stem.getId())) {
            audioRecorders.remove(stem.getId());
        }
    }

    public int updateStemPlayState(StemModel stem) {
        if (isPlayingStem(stem)) {
            audioPlayers.remove(stem.getId()).stop();
            return STATE_PLAYER_STOPPED;
        }

        if (stem.getBuffer() == null) {
            return STATE_PLAYER_UNAVAILABLE;
        }

        audioPlayers.put(stem.getId(), AudioFactory.getAudioPlayer(stem));
        return STATE_PLAYER_ACTIVE;
    }

    public int updateStemRecordState(StemModel stem) {
        if (isRecordingStem(stem)) {
            audioRecorders.remove(stem.getId()).stop();
            updateTrackPlayState();
            return STATE_RECORDER_STOPPED;
        }

        if (audioRecorders.size() != 0) {
            return STATE_RECORDER_UNAVAILABLE;
        }

        finalizeTrack();
        audioRecorders.put(stem.getId(), AudioFactory.getAudioRecorder(stem));
        updateTrackPlayState();
        return STATE_RECORDER_ACTIVE;
    }

    public int updateTrackPlayState() {
        if (audioPlayers.containsKey(ID_TRACK)) {
            Log.d(TAG, "updateTrackPlayState - STOPPED");
            audioPlayers.remove(ID_TRACK).stop();
            return STATE_PLAYER_STOPPED;
        }

        if (track.getBuffer() == null || audioPlayers.size() != 0) {
            Log.d(TAG, "updateTrackPlayState - UNAVAILABLE");
            return STATE_PLAYER_UNAVAILABLE;
        }

        Log.d(TAG, "updateTrackPlayState - ACTIVE");
        audioPlayers.put(ID_TRACK, AudioFactory.getAudioPlayer(finalizeTrack()));
        return STATE_PLAYER_ACTIVE;
    }

    public TrackModel finalizeTrack() {
        ShortBuffer stemBuffer;

        // The track buffer capacity is equal to the largest stem buffer capacity
        int trackBufferCapacity = 0;
        for (StemModel s : track.getStems()) {
            stemBuffer = s.getBuffer();
            if (stemBuffer != null && stemBuffer.capacity() > trackBufferCapacity) {
                trackBufferCapacity = stemBuffer.capacity();
            }
        }

        ShortBuffer trackBuffer = ShortBuffer.allocate(trackBufferCapacity);
        trackBuffer.rewind();

        // Summate all eligible stem samples at the current buffer position into one and clip it
        // http://stackoverflow.com/a/12090491/6633388
        int sample;
        while (trackBuffer.position() < trackBufferCapacity) {
            sample = 0;
            for (StemModel s : track.getStems()) {
                stemBuffer = s.getBuffer();
                if (stemBuffer != null && trackBuffer.position() < stemBuffer.capacity()) {
                    sample += stemBuffer.get(trackBuffer.position());
                }
            }
            sample = Math.max(Short.MIN_VALUE, Math.min(Short.MAX_VALUE, sample));
            trackBuffer.put((short)sample);
        }

        track.setBuffer(trackBuffer);

        return track;
    }

    private boolean isPlayingStem(StemModel stem) {
        return audioPlayers.containsKey(stem.getId());
    }

    private boolean isRecordingStem(StemModel stem) {
        return audioRecorders.containsKey(stem.getId());
    }
}
