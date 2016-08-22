package io.github.dstrekelj.pajamas.recorder;

import android.util.Log;

import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.toolkit.audio.PcmRecorderRunnable;

/**
 * TODO: Comment.
 */
public class StemRecorder extends PcmRecorderRunnable {
    public static final String TAG = "StemRecorder";

    public static final int STATE_RECORDING = 0;
    public static final int STATE_STOPPED = 1;

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
    protected void onRecord(short[] buffer, int numberOfRecordedShorts, long numberOfTotalRecordedShorts) {
        Log.d(TAG, "Recorded " + numberOfRecordedShorts + " samples (" + numberOfTotalRecordedShorts + " total) of stem " + stem.getTitle());
        for (int i = 0; i < numberOfRecordedShorts; i++) {
            sampleBuffer.add(buffer[i]);
        }
    }

    @Override
    protected void onRecordStart() {
        Log.d(TAG, "Started recording stem " + stem.getTitle());
        sampleBuffer = new ArrayList<>();
    }

    @Override
    protected void onRecordStop(long numberOfTotalRecordedShorts) {
        Log.d(TAG, "Stopped recording stem " + stem.getTitle() + " after " + numberOfTotalRecordedShorts + " samples");
        short[] buffer = new short[(int)numberOfTotalRecordedShorts];
        for (int i = 0; i < sampleBuffer.size(); i++) {
            buffer[i] = sampleBuffer.get(i);
        }
        stem.setBuffer(ShortBuffer.wrap(buffer));
    }
}
