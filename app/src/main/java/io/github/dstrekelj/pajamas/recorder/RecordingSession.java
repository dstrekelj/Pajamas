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

    public static final int STATE_STEM_PLAYER_ACTIVE = 0;
    public static final int STATE_STEM_PLAYER_STOPPED = 1;

    public static final int STATE_STEM_RECORDER_ACTIVE = 2;
    public static final int STATE_STEM_RECORDER_BUSY = 3;
    public static final int STATE_STEM_RECORDER_STOPPED = 4;

    public static final int STATE_TRACK_PLAYER_ACTIVE = 5;
    public static final int STATE_TRACK_PLAYER_STOPPED = 6;

    private HashMap<Integer, AudioPlayer> stemPlayers;
    private AudioRecorder audioRecorder;
    private TrackModel track;

    private boolean isTrackPlaying;
    private int numberOfCreatedStems;
    private int recordedStemId;

    public RecordingSession() {
        track = new TrackModel();
        track.setTitle("Untitled Track");
        track.setStems(new ArrayList<StemModel>());

        numberOfCreatedStems = 0;

        stemPlayers = new HashMap<>();

        isTrackPlaying = false;
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
        if (stemPlayers.containsKey(stem.getId())) {
            stemPlayers.remove(stem.getId());
        }
    }

    public int updateStemPlayState(StemModel stem) {
        if (stem.getBuffer() == null) {
            Log.d(TAG, "empty");
            return STATE_STEM_PLAYER_STOPPED;
        }
        if (isPlayingStem(stem)) {
            stemPlayers.get(stem.getId()).stop();
            stemPlayers.remove(stem.getId());
            return STATE_STEM_PLAYER_STOPPED;
        } else {
            stemPlayers.put(stem.getId(), AudioFactory.getAudioPlayer(stem));
            return STATE_STEM_PLAYER_ACTIVE;
        }
    }

    public int updateStemRecordState(StemModel stem) {
        if (isRecordingStem(stem)) {
            audioRecorder.stop();
            audioRecorder = null;
            recordedStemId = -1;
            for (StemModel s : track.getStems()) {
                if (s.getId() != stem.getId()) {
                    updateStemPlayState(s);
                }
            }
            return STATE_STEM_RECORDER_STOPPED;
        } else {
            if (audioRecorder == null) {
                audioRecorder = AudioFactory.getAudioRecorder(stem);
                recordedStemId = stem.getId();
                for (StemModel s : track.getStems()) {
                    if (s.getId() != stem.getId()) {
                        updateStemPlayState(s);
                    }
                }
                return STATE_STEM_RECORDER_ACTIVE;
            }
        }
        return STATE_STEM_RECORDER_BUSY;
    }

    public boolean isPlayingStem(StemModel stem) {
        return stemPlayers.containsKey(stem.getId());
    }

    public boolean isRecordingStem(StemModel stem) {
        return (audioRecorder != null) && (recordedStemId == stem.getId());
    }

    public int updateTrackPlayState() {
        for (StemModel stem : track.getStems()) {
            updateStemPlayState(stem);
        }
        isTrackPlaying = !isTrackPlaying;
        return isTrackPlaying ? STATE_TRACK_PLAYER_ACTIVE : STATE_TRACK_PLAYER_STOPPED;
    }

    public TrackModel finalizeTrack() {
        int maxCapacity = 0;
        for (StemModel s : track.getStems()) {
            if (s.getBuffer().capacity() > maxCapacity) {
                maxCapacity = s.getBuffer().capacity();
            }
        }
        ShortBuffer trackBuffer = ShortBuffer.allocate(maxCapacity);
        trackBuffer.rewind();
        // http://stackoverflow.com/a/12090491/6633388
        int sample;
        while (trackBuffer.position() < maxCapacity) {
            sample = 0;
            for (StemModel s : track.getStems()) {
                if (trackBuffer.position() < s.getBuffer().capacity()) {
                    sample += s.getBuffer().get(trackBuffer.position());
                }
            }
            if (sample > Short.MAX_VALUE) {
                sample = Short.MAX_VALUE;
            }
            if (sample < Short.MIN_VALUE) {
                sample = Short.MIN_VALUE;
            }
            trackBuffer.put((short)sample);
        }

        track.setBuffer(trackBuffer);

        return track;
    }
}
