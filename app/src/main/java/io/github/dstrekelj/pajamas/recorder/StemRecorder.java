package io.github.dstrekelj.pajamas.recorder;

import android.util.Log;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.toolkit.audio.PcmRecorderRunnable;

/**
 * TODO: Comment.
 */
public class StemRecorder extends PcmRecorderRunnable {
    public static final String TAG = "StemRecorder";

    private StemModel stem;

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
    }

    @Override
    protected void onRecordStart() {
        Log.d(TAG, "Started recording stem " + stem.getTitle());
    }

    @Override
    protected void onRecordStop(long numberOfRecordedSamples) {
        Log.d(TAG, "Stopped recording stem " + stem.getTitle() + " after " + numberOfRecordedSamples + " samples");
    }
}
