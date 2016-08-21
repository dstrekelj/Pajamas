package io.github.dstrekelj.toolkit.audio;

import android.media.AudioRecord;
import android.os.Process;
import android.util.Log;

/**
 * TODO: Comment.
 */
public abstract class PcmRecorderRunnable implements Runnable {
    public static final String TAG = "PcmRecorderRunnable";

    private int audioFormat;
    private int audioSource;
    private int channelConfiguration;
    private int sampleRate;

    private boolean isRecording;

    public PcmRecorderRunnable(int audioFormat, int audioSource, int channelConfiguration, int sampleRate) {
        this.audioFormat = audioFormat;
        this.audioSource = audioSource;
        this.channelConfiguration = channelConfiguration;
        this.sampleRate = sampleRate;
    }

    public void stop() {
        isRecording = false;
    }

    @Override
    public void run() {
        isRecording = true;

        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO);

        int minBufferSize = AudioRecord.getMinBufferSize(
                sampleRate,
                channelConfiguration,
                audioFormat
        );

        if (minBufferSize == AudioRecord.ERROR || minBufferSize == AudioRecord.ERROR_BAD_VALUE) {
            minBufferSize = sampleRate * 2;
        }

        short[] buffer = new short[minBufferSize / 2];

        AudioRecord record = new AudioRecord(
                audioSource,
                sampleRate,
                channelConfiguration,
                audioFormat,
                minBufferSize
        );

        if (record.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e(TAG, "Failed initializing recorder!");
            return;
        }

        record.startRecording();
        onRecordStart();

        long samplesRead = 0;
        while (isRecording) {
            int bytesRead = record.read(
                    buffer,
                    0,
                    buffer.length
            );
            samplesRead += bytesRead;

            onRecord(buffer, bytesRead, samplesRead);
        }

        record.stop();
        onRecordStop(samplesRead);

        record.release();
    }

    protected abstract void onRecord(short[] buffer, int numberOfRecordedBytes, long numberOfRecordedSamples);
    protected abstract void onRecordStart();
    protected abstract void onRecordStop(long numberOfRecordedSamples);
}
