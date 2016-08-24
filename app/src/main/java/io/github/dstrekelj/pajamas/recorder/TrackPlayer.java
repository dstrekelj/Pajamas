package io.github.dstrekelj.pajamas.recorder;

import android.util.Log;

import java.nio.ShortBuffer;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.models.TrackModel;
import io.github.dstrekelj.toolkit.audio.PcmPlayerRunnable;

/**
 * TODO: Comment.
 */
public class TrackPlayer extends PcmPlayerRunnable {
    public static final String TAG = "TrackPlayer";

    private TrackModel track;

    public TrackPlayer(int audioFormat, int channelConfiguration, int sampleRate, int streamType) {
        super(audioFormat, channelConfiguration, sampleRate, streamType);
    }

    public void setTrack(TrackModel track) {
        this.track = track;
    }

    @Override
    protected void onPlay() {
    }

    @Override
    protected ShortBuffer onPlayerStart() {
        Log.d(TAG, "Started playing track " + track.getTitle());
        return track.getBuffer();
    }

    @Override
    protected void onPlayerStop() {
        Log.d(TAG, "Stopped playing track " + track.getTitle());
    }
}
