package io.github.dstrekelj.pajamas.recorder;

import android.util.Log;

import java.nio.ShortBuffer;

import io.github.dstrekelj.pajamas.models.AudioModel;
import io.github.dstrekelj.toolkit.audio.PcmPlayerRunnable;

/**
 * An extension of the PCM recorder pre-configured to play audio.
 */
public class AudioPlayer extends PcmPlayerRunnable {
    public static final String TAG = "AudioPlayer";

    private AudioModel audio;

    public AudioPlayer(int audioFormat, int sampleRate, int channelConfiguration, int streamType) {
        super(audioFormat, channelConfiguration, sampleRate, streamType);
    }

    public void setAudio(AudioModel audio) {
        this.audio = audio;
    }

    @Override
    protected void onPlay() {}

    @Override
    protected ShortBuffer onPlayerStart() {
        Log.d(TAG, "Started playing...");
        return audio.getBuffer();
    }

    @Override
    protected void onPlayerStop() {
        Log.d(TAG, "Stopped playing...");
    }
}
