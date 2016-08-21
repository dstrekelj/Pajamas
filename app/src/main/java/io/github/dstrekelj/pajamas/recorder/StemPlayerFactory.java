package io.github.dstrekelj.pajamas.recorder;

import android.media.AudioFormat;
import android.media.AudioManager;

import io.github.dstrekelj.pajamas.models.StemModel;

/**
 * TODO: Comment.
 */
public class StemPlayerFactory {
    public static final String TAG = "StemPlayerFactory";

    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    public static final int CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_IN_MONO;
    public static final int SAMPLE_RATE = 44100;
    public static final int STREAM_TYPE = AudioManager.STREAM_MUSIC;

    public static StemPlayer getStemPlayer(StemModel stem) {
        StemPlayer stemPlayer = new StemPlayer(
                AUDIO_FORMAT,
                CHANNEL_CONFIGURATION,
                SAMPLE_RATE,
                STREAM_TYPE
        );
        stemPlayer.setStem(stem);
        new Thread(stemPlayer).run();
        return stemPlayer;
    }
}
