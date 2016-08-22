package io.github.dstrekelj.pajamas.recorder;

import android.util.Log;

import java.nio.ShortBuffer;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.toolkit.audio.PcmPlayerRunnable;

/**
 * TODO: Comment.
 */
public class StemPlayer extends PcmPlayerRunnable {
    public static final String TAG = "StemPlayer";

    public static final int STATE_PLAYING = 0;
    public static final int STATE_STOPPED = 1;

    private StemModel stem;

    public StemPlayer(int audioFormat, int channelConfiguration, int sampleRate, int streamType) {
        super(audioFormat, channelConfiguration, sampleRate, streamType);
    }

    public void setStem(StemModel stem) {
        this.stem = stem;
    }

    @Override
    protected void onPlay() {
        Log.d(TAG, "Playing stem " + stem.getTitle());
    }

    @Override
    protected ShortBuffer onPlayerStart() {
        Log.d(TAG, "Started playing stem " + stem.getTitle());
        return stem.getBuffer();
    }

    @Override
    protected void onPlayerStop() {
        Log.d(TAG, "Stopped playing stem " + stem.getTitle());
    }
}
