package io.github.dstrekelj.pajamas.recorder;

import android.media.AudioFormat;
import android.media.MediaRecorder;

import io.github.dstrekelj.pajamas.models.StemModel;

/**
 * TODO: Comment.
 */
public class StemRecorderFactory {
    public static final String TAG = "StemRecorderFactory";

    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    public static final int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    public static final int CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_IN_MONO;
    public static final int SAMPLE_RATE = 11025;

    public static StemRecorder getStemRecorder(StemModel stem) {
        StemRecorder stemRecorder = new StemRecorder(
                AUDIO_FORMAT,
                AUDIO_SOURCE,
                CHANNEL_CONFIGURATION,
                SAMPLE_RATE
        );
        stemRecorder.setStem(stem);
        new Thread(stemRecorder).start();
        return stemRecorder;
    }
}
