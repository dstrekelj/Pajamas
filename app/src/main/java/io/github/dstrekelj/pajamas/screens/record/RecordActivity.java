package io.github.dstrekelj.pajamas.screens.record;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.dstrekelj.pajamas.R;

public class RecordActivity extends AppCompatActivity implements RecordContract.View {
    public static final String TAG = "RecordActivity";

    @BindView(R.id.activity_record_btn_play)
    Button btnPlay;

    @BindView(R.id.activity_record_btn_record)
    Button btnRecord;

    @BindView(R.id.activity_record_et_track_name)
    EditText etTrackName;

    private RecordContract.Presenter presenter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        unbinder = ButterKnife.bind(this);

        presenter = new RecordPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.destroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.activity_record_btn_play)
    void onClickPlay() {
        presenter.changePlayState();
    }

    @OnClick(R.id.activity_record_btn_record)
    void onClickRecord() {
        presenter.setTrack(etTrackName.getText().toString());
        presenter.changeRecordState();
    }

    @Override
    public void updatePlayState(boolean isPlaying) {
        btnPlay.setText(getResources().getString(isPlaying ? R.string.playing_stop : R.string.playing_start));
        btnRecord.setEnabled(!isPlaying);
    }

    @Override
    public void updateRecordState(boolean isRecording) {
        btnRecord.setText(getResources().getString(isRecording ? R.string.recording_stop : R.string.recording_start));
        btnPlay.setEnabled(!isRecording);
    }
}
