package io.github.dstrekelj.pajamas.recorder;

import android.util.Log;

import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import io.github.dstrekelj.pajamas.models.AudioModel;
import io.github.dstrekelj.toolkit.audio.PcmRecorderRunnable;

/**
 * An extension of the PCM recorder pre-configured to record audio.
 */
public class AudioRecorder extends PcmRecorderRunnable {
    public static final String TAG = "AudioRecorder";

    private AudioModel audio;
    private List<Short> tempBuffer;

    public AudioRecorder(int audioFormat, int sampleRate, int channelConfiguration, int audioSource) {
        super(audioFormat, audioSource, channelConfiguration, sampleRate);
    }

    public void setAudio(AudioModel audio) {
        this.audio = audio;
    }

    @Override
    protected void onRecord(short[] buffer, int numberOfRecordedShorts, long numberOfTotalRecordedShorts) {
//        long startTime = System.nanoTime();

        for (int i = 0; i < numberOfRecordedShorts; i++) {
            tempBuffer.add(buffer[i]);
        }

//        long endTime = System.nanoTime() - startTime;
//        Log.d(TAG, "Duration: " + endTime);
    }

    @Override
    protected void onRecordStart() {
        Log.d(TAG, "Started recording...");
        tempBuffer = new ArrayList<>();
    }

    @Override
    protected void onRecordStop(long numberOfTotalRecordedShorts) {
        Log.d(TAG, "Stopped recording (" + numberOfTotalRecordedShorts + " samples recorded)");
        short[] bufferData = new short[(int)numberOfTotalRecordedShorts];
        for (int i = 0; i < tempBuffer.size(); i++) {
            bufferData[i] = tempBuffer.get(i);
        }
        ShortBuffer buffer = ShortBuffer.wrap(bufferData);
        buffer.rewind();
        audio.setBuffer(buffer);
    }
}
