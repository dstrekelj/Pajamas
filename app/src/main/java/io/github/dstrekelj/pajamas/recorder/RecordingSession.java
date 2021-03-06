package io.github.dstrekelj.pajamas.recorder;

import android.util.Log;

import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import io.github.dstrekelj.pajamas.models.AudioModel;
import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.models.TrackModel;

/**
 * A recording session handles the current track and track stems, their recording, and playback.
 */
public class RecordingSession {
    public static final String TAG = "RecordingSession";

    public static final int STATE_ACTIVE = 1;
    public static final int STATE_INACTIVE = 2;
    public static final int STATE_UNAVAILABLE = 3;

    private static final int ID_TRACK = -1;

    private HashMap<Integer, AudioPlayer> audioPlayers;
    private HashMap<Integer, AudioRecorder> audioRecorders;
    private TrackModel track;

    private final String defaultStemTitle;

    private boolean isTrackDirty;
    private int numberOfCreatedStems;

    public RecordingSession(String defaultTrackTitle, String defaultStemTitle) {
        audioPlayers = new HashMap<>();
        audioRecorders = new HashMap<>();

        track = new TrackModel();
        track.setStems(new ArrayList<StemModel>());
        track.setTitle(defaultTrackTitle);

        this.defaultStemTitle = defaultStemTitle;

        isTrackDirty = true;
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

    public TrackModel getTrack() {
        return track;
    }

    /**
     * Add track stem.
     * @return  Stem
     */
    public StemModel addStem() {
        isTrackDirty = true;

        StemModel stem = new StemModel();
        stem.setId(numberOfCreatedStems);
        stem.setTitle((numberOfCreatedStems == 0) ? defaultStemTitle : defaultStemTitle + " #" + numberOfCreatedStems);

        track.getStems().add(stem);

        numberOfCreatedStems += 1;

        return stem;
    }

    /**
     * Remove track stem.
     * @param stem  Stem
     */
    public void removeStem(StemModel stem) {
        isTrackDirty = true;

        track.getStems().remove(stem);
        if (audioPlayers.containsKey(stem.getId())) {
            audioPlayers.remove(stem.getId());
        }
        if (audioRecorders.containsKey(stem.getId())) {
            audioRecorders.remove(stem.getId());
        }
    }

    /**
     * Finalize track by mixing all stems into one buffer.
     * @return  Track
     */
    public TrackModel finalizeTrack() {
        return mixStems(null);
    }

    /**
     * Request a preview of the track.
     * @param recordedStem  The currently recorded stem that requires the preview during recording
     * @return              Track
     */
    public TrackModel previewTrack(StemModel recordedStem) {
        return mixStems(recordedStem);
    }

    /**
     * Mixes all recorded track stems into track audio buffer.
     * @param recordedStem  The currently recorded stem that will be excluded from mixing
     * @return              Track
     */
    private TrackModel mixStems(StemModel recordedStem) {
        if (!isTrackDirty) {
            Log.d(TAG, "No need to finalize");
            return track;
        }

        if (track.getStems().isEmpty()) {
            Log.d(TAG, "Empty stems");
            track.setBuffer(null);
            return track;
        }

        ShortBuffer stemBuffer;

        // The track buffer capacity is equal to the largest stem buffer capacity
        int trackBufferCapacity = 0;
        for (StemModel stem : track.getStems()) {
            stemBuffer = stem.getBuffer();
            if (isAudioReady(stem) && stemBuffer.capacity() > trackBufferCapacity) {
                if (recordedStem != null && recordedStem.getId() == stem.getId()) {
                    continue;
                }
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
            for (StemModel stem : track.getStems()) {
                stemBuffer = stem.getBuffer();
                if (isAudioReady(stem) && !isRecordingStem(stem) && trackBuffer.position() < stemBuffer.capacity()) {
                    if (recordedStem != null && recordedStem.getId() == stem.getId()) {
                        continue;
                    }
                    sample += stemBuffer.get(trackBuffer.position());
                }
            }
            sample = Math.max(Short.MIN_VALUE, Math.min(Short.MAX_VALUE, sample));
            trackBuffer.put((short)sample);
        }

        track.setBuffer(trackBuffer);

        isTrackDirty = false;

        return track;
    }

    /**
     * Determines the current stem player state.
     * @param stem  Stem
     * @return      STEM_ACTIVE         Stem is currently being played
     *              STEM_INACTIVE       Stem is currently not being played
     *              STEM_UNAVAILABLE    Stem audio is not ready
     */
    public int getStemPlayerState(StemModel stem) {
        if (isPlayingStem(stem)) {
            return STATE_ACTIVE;
        }

        if (isAudioReady(stem)) {
            return STATE_INACTIVE;
        }

        return STATE_UNAVAILABLE;
    }

    /**
     * Determines the current stem recorder state.
     * @param stem  Stem
     * @return      STATE_ACTIVE        Stem is currently being recorded
     *              STATE_INACTIVE      Stem is currently not being recorded
     *              STATE_UNAVAILABLE   Stem recorder is busy
     */
    public int getStemRecorderState(StemModel stem) {
        if (isRecordingStem(stem)) {
            return STATE_ACTIVE;
        }

        if (audioRecorders.size() == 0) {
            return STATE_INACTIVE;
        }

        return STATE_UNAVAILABLE;
    }

    /**
     * Determines the current track player state.
     * @return  STATE_ACTIVE        Track is currently being played
     *          STATE_INACTIVE      Track is currently not being played
     *          STATE_UNAVAILABLE   Track audio is not ready
     */
    public int getTrackPlayerState() {
        if (isPlayingTrack()) {
            return STATE_ACTIVE;
        }

        if (isAudioReady(track)) {
            return STATE_INACTIVE;
        }

        return STATE_UNAVAILABLE;
    }

    /**
     * Checks if the audio data is ready.
     * @param audio Audio
     * @return      `true` if audio data buffer is not null
     */
    public boolean isAudioReady(AudioModel audio) {
        return audio.getBuffer() != null;
    }

    /**
     * Checks if the stem is currently being played.
     * @param stem  Stem
     * @return      `true` if stem is currently being played
     */
    public boolean isPlayingStem(StemModel stem) {
        return audioPlayers.containsKey(stem.getId());
    }

    /**
     * Checks if the track is currently being played.
     * @return  `true` if track is being played
     */
    public boolean isPlayingTrack() {
        return audioPlayers.containsKey(ID_TRACK);
    }

    /**
     * Checks if the stem is currently being recorded.
     * @param stem  Stem
     * @return      `true` if stem is being recorded
     */
    public boolean isRecordingStem(StemModel stem) {
        return audioRecorders.containsKey(stem.getId());
    }

    /**
     * Starts the playback of a specific stem.
     * @param stem  Stem
     */
    public void startStemPlayback(StemModel stem) {
        audioPlayers.put(stem.getId(), AudioFactory.getAudioPlayer(stem));
    }

    /**
     * Starts the recording of a specific stem.
     * @param stem  Stem
     */
    public void startStemRecording(StemModel stem) {
        isTrackDirty = true;
        audioRecorders.put(stem.getId(), AudioFactory.getAudioRecorder(stem));
    }

    /**
     * Starts track playback.
     */
    public void startTrackPlayback() {
        audioPlayers.put(ID_TRACK, AudioFactory.getAudioPlayer(track));
    }

    /**
     * Stops the playback of a specific stem.
     * @param stem  Stem
     */
    public void stopStemPlayback(StemModel stem) {
        audioPlayers.remove(stem.getId()).stop();
    }

    /**
     * Stops the recording of a specific stem.
     * @param stem  Stem
     */
    public void stopStemRecording(StemModel stem) {
        audioRecorders.remove(stem.getId()).stop();
    }

    /**
     * Stops track playback.
     */
    public void stopTrackPlayback() {
        audioPlayers.remove(ID_TRACK).stop();
    }
}
