package io.github.dstrekelj.toolkit.audio;

import android.media.AudioTrack;
import android.os.Process;
import android.util.Log;

import java.nio.ShortBuffer;

/**
 * A `Runnable` which performs PCM audio playback.
 */
public abstract class PcmPlayerRunnable implements Runnable, AudioTrack.OnPlaybackPositionUpdateListener {
    public static final String TAG = "PcmPlayerRunnable";

    private int audioFormat;
    private int channelConfiguration;
    private int sampleRate;
    private int streamType;

    private boolean isPlaying;

    public PcmPlayerRunnable(int audioFormat, int channelConfiguration, int sampleRate, int streamType) {
        this.audioFormat = audioFormat;
        this.channelConfiguration = channelConfiguration;
        this.sampleRate = sampleRate;
        this.streamType = streamType;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void stop() {
        Log.d(TAG, "Stopping playback");
        isPlaying = false;
    }

    @Override
    public void run() {
        isPlaying = true;

        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);

        int minBufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                channelConfiguration,
                audioFormat
        );

        if (minBufferSize == AudioTrack.ERROR || minBufferSize == AudioTrack.ERROR_BAD_VALUE) {
            minBufferSize = sampleRate * 2;
        }

        AudioTrack audioTrack = new AudioTrack(
                streamType,
                sampleRate,
                channelConfiguration,
                audioFormat,
                minBufferSize,
                AudioTrack.MODE_STREAM
        );

        ShortBuffer samples = onPlayerStart();

        audioTrack.setPlaybackPositionUpdateListener(this);
        audioTrack.setPositionNotificationPeriod(sampleRate / 30);
        audioTrack.setNotificationMarkerPosition(samples.capacity());

        audioTrack.play();

        short[] buffer = new short[minBufferSize];
        samples.rewind();
        int limit = samples.capacity();
        while (samples.position() < limit && isPlaying) {
            int numberOfLeftoverSamples = limit - samples.position();
            int numberOfSamplesToWrite;
            if (numberOfLeftoverSamples >= buffer.length) {
                samples.get(buffer);
                numberOfSamplesToWrite = buffer.length;
            } else {
                for (int i = numberOfLeftoverSamples; i < buffer.length; i++) {
                    buffer[i] = 0;
                }
                samples.get(buffer, 0, numberOfLeftoverSamples);
                numberOfSamplesToWrite = numberOfLeftoverSamples;
            }
            audioTrack.write(buffer, 0, numberOfSamplesToWrite);
            onPlay();
        }

        if (!isPlaying) {
            audioTrack.release();
            onPlayerStop();
        }
    }

    @Override
    public void onMarkerReached(AudioTrack audioTrack) {
        audioTrack.release();
        onPlayerStop();
    }

    @Override
    public void onPeriodicNotification(AudioTrack audioTrack) {

    }

    protected abstract void onPlay();
    protected abstract ShortBuffer onPlayerStart();
    protected abstract void onPlayerStop();
}
