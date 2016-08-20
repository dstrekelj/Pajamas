package io.github.dstrekelj.pajamas.recorder.runnables;

import android.media.AudioRecord;
import android.os.Process;
import android.util.Log;

/**
 * TODO: Comment.
 */
public class RecordRunnable implements Runnable {
    public static final String TAG = "RecordRunnable";

    private int audioSource;
    private int sampleRate;
    private int channelConfiguration;
    private int audioFormat;
    private boolean isRecording = true;

    public RecordRunnable() {
    }

    public RecordRunnable(int audioSource, int sampleRate, int channelConfiguration, int audioFormat) {
        this.audioSource = audioSource;
        this.sampleRate = sampleRate;
        this.channelConfiguration = channelConfiguration;
        this.audioFormat = audioFormat;
    }

    public int getAudioSource() {
        return audioSource;
    }

    public void setAudioSource(int audioSource) {
        this.audioSource = audioSource;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public int getChannelConfiguration() {
        return channelConfiguration;
    }

    public void setChannelConfiguration(int channelConfiguration) {
        this.channelConfiguration = channelConfiguration;
    }

    public int getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(int audioFormat) {
        this.audioFormat = audioFormat;
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

        long samplesRead = 0;
        while (isRecording) {
            int sampleNumber = record.read(
                    buffer,
                    0,
                    buffer.length
            );
            samplesRead += sampleNumber;
        }
        Log.d(TAG, "Recorded " + samplesRead + " samples");
        record.stop();
        record.release();
    }
}
