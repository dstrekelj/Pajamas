package io.github.dstrekelj.pajamas.screens.record;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.dstrekelj.pajamas.R;
import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.recorder.RecordingSession;
import io.github.dstrekelj.pajamas.recorder.StemPlayer;
import io.github.dstrekelj.pajamas.recorder.StemRecorder;
import io.github.dstrekelj.pajamas.screens.record.adapters.StemItemsAdapter;

public class RecordActivity extends AppCompatActivity implements RecordContract.View, StemItemsAdapter.StemItemsAdapterListener {
    public static final String TAG = "RecordActivity";

    @BindView(R.id.activity_record_fab_add_stem)
    FloatingActionButton fabAddStem;
    @BindView(R.id.activity_record_rv_stems)
    RecyclerView rvStems;
    @BindView(R.id.component_record_track_btn_finalize_track)
    Button btnFinalizeTrack;
    @BindView(R.id.component_record_track_btn_play_track)
    Button btnPlayTrack;
    @BindView(R.id.component_record_track_et_track_title)
    EditText etTrackTitle;

    private RecordContract.Presenter presenter;
    private StemItemsAdapter adapter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        unbinder = ButterKnife.bind(this);

        adapter = new StemItemsAdapter(this);
        adapter.setListener(this);
        rvStems.setAdapter(adapter);
        rvStems.setLayoutManager(new LinearLayoutManager(this));

        presenter = new RecordPresenter(this);

        presenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.destroy();

        adapter = null;
        unbinder = null;
        presenter = null;
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

    @Override
    public void onStemRecord(StemModel stem, Button button) {
        int state = presenter.updateStemRecordState(stem);
        switch (state) {
            case RecordingSession.STEM_RECORDER_ACTIVE:
                Log.d(TAG, "Stopping recording");
                button.setText(R.string.stem_stop);
                break;
            case RecordingSession.STEM_RECORDER_STOPPED:
                Log.d(TAG, "Starting recording");
                button.setText(R.string.stem_record);
                break;
            default:
        }
    }

    @Override
    public void onStemPlay(StemModel stem, Button button) {
        int state = presenter.updateStemPlayState(stem);
        switch (state) {
            case RecordingSession.STEM_PLAYER_ACTIVE:
                button.setText(R.string.stem_stop);
                break;
            case RecordingSession.STEM_PLAYER_STOPPED:
                button.setText(R.string.stem_play);
                break;
            default:
        }
    }

    @Override
    public void onStemRemove(StemModel stem, Button button) {
        presenter.deleteStem(stem);
    }

    @Override
    public void displayStemInsertion(StemModel stem) {
        adapter.insertItem(stem);
        rvStems.getLayoutManager().scrollToPosition(adapter.getItemPosition(stem));
    }

    @Override
    public void displayStemRemoval(StemModel stem) {
        adapter.removeItem(stem);
    }

    @Override
    public void displayTrackTitle(String title) {
        etTrackTitle.setText(title);
    }

    @OnClick(R.id.activity_record_fab_add_stem) void onClickAddStem() {
        presenter.createStem();
    }

    @OnClick(R.id.component_record_track_btn_finalize_track) void onClickFinalizeTrack() {
        presenter.finalizeTrack();
    }

    @OnClick(R.id.component_record_track_btn_play_track) void onClickPlayTrack() {
        int state = presenter.updateTrackPlayState();
        switch (state) {

        }
    }
}
