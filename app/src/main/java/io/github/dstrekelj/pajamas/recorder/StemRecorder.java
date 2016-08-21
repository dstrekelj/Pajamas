package io.github.dstrekelj.pajamas.recorder;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.toolkit.audio.PcmRecorderRunnable;

/**
 * TODO: Comment.
 */
public class StemRecorder extends PcmRecorderRunnable {
    public static final String TAG = "StemRecorder";

    private StemModel stem;
    private List<Short> sampleBuffer;

    public StemRecorder(int audioFormat, int audioSource, int channelConfiguration, int sampleRate) {
        super(audioFormat, audioSource, channelConfiguration, sampleRate);
    }

    public StemModel getStem() {
        return stem;
    }

    public void setStem(StemModel stem) {
        this.stem = stem;
    }

    @Override
    protected void onRecord(short[] buffer, int numberOfRecordedBytes, long numberOfRecordedSamples) {
        Log.d(TAG, "Recorded " + numberOfRecordedBytes + " bytes of stem " + stem.getTitle());
        for (int i = 0; i < numberOfRecordedBytes; i += 2) {
            sampleBuffer.add((short) (buffer[i+1] << 8 & buffer[i]));
        }
    }

    @Override
    protected void onRecordStart() {
        Log.d(TAG, "Started recording stem " + stem.getTitle());
        sampleBuffer = new ArrayList<>();
    }

    @Override
    protected void onRecordStop(long numberOfRecordedSamples) {
        Log.d(TAG, "Stopped recording stem " + stem.getTitle() + " after " + numberOfRecordedSamples + " samples");
        Log.d(TAG, sampleBuffer.size()+"");
        short[] buffer = new short[(int)numberOfRecordedSamples / 2];
        for (int i = 1; i < sampleBuffer.size(); i++) {
            buffer[i] = sampleBuffer.get(i);
        }
        stem.setBuffer(buffer);
    }
}
