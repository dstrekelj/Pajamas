package io.github.dstrekelj.pajamas.screens.record;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * TODO: Comment.
 */
public class RecordPresenter implements RecordContract.Presenter {
    public static final String TAG = "RecordPresenter";

    public static String file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test";

    private RecordContract.View view;
    private MediaRecorder recorder;
    private MediaPlayer player;

    private boolean isPlaying = false;
    private boolean isRecording = false;

    public RecordPresenter(RecordContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        recorder = null;
        player = null;
    }

    @Override
    public void start() {
        view.updatePlayState(isPlaying);
        view.updateRecordState(isRecording);
    }

    @Override
    public void stop() {
        if (isPlaying) stopPlaying();
        if (isRecording) stopRecording();
    }

    @Override
    public void changePlayState() {
        if (isPlaying) {
            stopPlaying();
        } else {
            startPlaying();
        }
        isPlaying = !isPlaying;
        view.updatePlayState(isPlaying);
    }

    @Override
    public void changeRecordState() {
        if (isRecording) {
            stopRecording();
        } else {
            startRecording();
        }
        isRecording = !isRecording;
        view.updateRecordState(isRecording);
    }

    private void startPlaying() {
        player = new MediaPlayer();

        try {
            player.setDataSource(file);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(TAG, "player.setDataSource() failed");
            e.printStackTrace();
        }
    }

    private void stopPlaying() {
        player.stop();
        player.reset();
        player.release();
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setOutputFile(file);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e(TAG, "recorder.prepare() failed");
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        recorder.stop();
        recorder.reset();
        recorder.release();
    }
}
