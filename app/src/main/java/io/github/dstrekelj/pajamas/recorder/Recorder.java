package io.github.dstrekelj.pajamas.recorder;

import android.media.AudioFormat;
import android.media.MediaRecorder;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.recorder.runnables.RecordRunnable;

/**
 * TODO: Comment.
 */
public class Recorder {
    public static final String TAG = "Recorder";

    private static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    private static final int RECORD_CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_IN_MONO;
    private static final int PLAYBACK_CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_OUT_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static final int SAMPLE_RATE = 44100;

    private static RecordRunnable recordRunnable = new RecordRunnable(
            AUDIO_SOURCE,
            SAMPLE_RATE,
            RECORD_CHANNEL_CONFIGURATION,
            AUDIO_FORMAT
    );

    public static void record(StemModel stem) {
        new Thread(recordRunnable).start();
    }

    public static void stop(StemModel stem) {
        recordRunnable.stop();
    }
}
