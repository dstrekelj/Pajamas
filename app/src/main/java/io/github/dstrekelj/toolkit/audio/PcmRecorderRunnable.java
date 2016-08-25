package io.github.dstrekelj.toolkit.audio;

import android.media.AudioRecord;
import android.os.Process;
import android.util.Log;

/**
 * A `Runnable` which performs PCM audio recording.
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

    public boolean isRecording() {
        return isRecording;
    }

    public void stop() {
        isRecording = false;
    }

    @Override
    public void run() {
        isRecording = true;

        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);

        int minBufferSize = AudioRecord.getMinBufferSize(
                sampleRate,
                channelConfiguration,
                audioFormat
        );

        if (minBufferSize == AudioRecord.ERROR || minBufferSize == AudioRecord.ERROR_BAD_VALUE) {
            Log.d(TAG, "Unable to detect minimum buffer size");
            minBufferSize = sampleRate * 2;
        }
        Log.d(TAG, "Min buffer size (B): " + minBufferSize);

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

        long numberOfTotalRecordedShorts = 0;
        while (isRecording) {
            int numberOfRecordedShorts = record.read(
                    buffer,
                    0,
                    buffer.length
            );
            numberOfTotalRecordedShorts += numberOfRecordedShorts;

            onRecord(buffer, numberOfRecordedShorts, numberOfTotalRecordedShorts);
        }

        record.stop();
        record.release();

        onRecordStop(numberOfTotalRecordedShorts);
    }

    protected abstract void onRecord(short[] buffer, int numberOfRecordedShorts, long numberOfTotalRecordedShorts);
    protected abstract void onRecordStart();
    protected abstract void onRecordStop(long numberOfTotalRecordedShorts);
}
