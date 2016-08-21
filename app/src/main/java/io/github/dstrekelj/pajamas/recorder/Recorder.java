package io.github.dstrekelj.pajamas.recorder;

import android.media.AudioFormat;
import android.media.MediaRecorder;

import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.util.PcmRecorderRunnable;

/**
 * TODO: Comment.
 */
public class Recorder {
    public static final String TAG = "Recorder";

    public static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    public static final int RECORD_CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_IN_MONO;
    public static final int PLAYBACK_CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_OUT_MONO;
    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    public static final int SAMPLE_RATE = 44100;

    public static StemRecorder record(StemModel stem) {
        StemRecorder stemRecorder = new StemRecorder(
                AUDIO_FORMAT,
                AUDIO_SOURCE,
                RECORD_CHANNEL_CONFIGURATION,
                SAMPLE_RATE
        );
        stemRecorder.setStem(stem);
        new Thread(stemRecorder).start();
        return stemRecorder;
    }
}
