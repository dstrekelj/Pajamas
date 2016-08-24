package io.github.dstrekelj.pajamas.recorder;

import android.media.AudioFormat;
import android.media.AudioManager;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.models.TrackModel;

/**
 * TODO: Comment.
 */
public class TrackPlayerFactory {
    public static final String TAG = "TrackPlayerFactory";

    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    public static final int CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_OUT_MONO;
    public static final int SAMPLE_RATE = 11025;
    public static final int STREAM_TYPE = AudioManager.STREAM_MUSIC;

    public static TrackPlayer getTrackPlayer(TrackModel track) {
        TrackPlayer trackPlayer = new TrackPlayer(
                AUDIO_FORMAT,
                CHANNEL_CONFIGURATION,
                SAMPLE_RATE,
                STREAM_TYPE
        );
        trackPlayer.setTrack(track);
        new Thread(trackPlayer).start();
        return trackPlayer;
    }
}
