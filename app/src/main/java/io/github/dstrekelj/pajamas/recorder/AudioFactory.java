package io.github.dstrekelj.pajamas.recorder;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.MediaRecorder;

import io.github.dstrekelj.pajamas.models.AudioModel;

/**
 * Creates pre-configured audio players and recorders.
 */
public class AudioFactory {
    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    public static final int SAMPLE_RATE = 11025;

    public static final int PLAYER_CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_OUT_MONO;
    public static final int PLAYER_STREAM_TYPE = AudioManager.STREAM_MUSIC;

    public static final int RECORDER_AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    public static final int RECORDER_CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_IN_MONO;

    public static AudioPlayer getAudioPlayer(AudioModel audio) {
        AudioPlayer audioPlayer = new AudioPlayer(
                AUDIO_FORMAT,
                SAMPLE_RATE,
                PLAYER_CHANNEL_CONFIGURATION,
                PLAYER_STREAM_TYPE
        );
        audioPlayer.setAudio(audio);
        new Thread(audioPlayer).start();
        return audioPlayer;
    }

    public static AudioRecorder getAudioRecorder(AudioModel audio) {
        AudioRecorder audioRecorder = new AudioRecorder(
                AUDIO_FORMAT,
                SAMPLE_RATE,
                RECORDER_CHANNEL_CONFIGURATION,
                RECORDER_AUDIO_SOURCE
        );
        audioRecorder.setAudio(audio);
        new Thread(audioRecorder).start();
        return audioRecorder;
    }
}
