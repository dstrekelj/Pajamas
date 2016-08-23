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
import io.github.dstrekelj.pajamas.screens.record.views.StemView;

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

    /*
    * LIFECYCLE METHODS
    * */

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

    /*
    * VISUAL CUES
    * */

    @Override
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

    /**
     * Notifies the StemView of a change in stem recording state, so that the StemView can visually
     * update according to the new state.
     *
     * @param stemView  Affected StemView
     * @param state     Stem state, from RecordingSession
     */
    private void displayStemViewRecordState(StemView stemView, int state) {
        stemView.setStemRecordState(state);
    }

    /**
     * Notifies the StemView of a change in stem play state, so that the StemView can visually
     * update according to the new state.
     *
     * @param stemView  Affected StemView
     * @param state     Stem state, from RecordngSession
     */
    private void displayStemViewPlayState(StemView stemView, int state) {
        stemView.setStemPlayState(state);
    }

    /*
    * USER INTERACTION
    * */

    @OnClick(R.id.activity_record_fab_add_stem) void onClickAddStem() {
        presenter.createStem();
    }

    @OnClick(R.id.component_record_track_btn_finalize_track) void onClickFinalizeTrack() {
        presenter.finalizeTrack();
    }

    @OnClick(R.id.component_record_track_btn_play_track) void onClickPlayTrack() {
        presenter.updateTrackPlayState();
    }

    @Override
    public void onStemPlay(StemModel stem, StemView stemView) {
        int state = presenter.updateStemPlayState(stem);
        displayStemViewPlayState(stemView, state);
    }

    @Override
    public void onStemRecord(StemModel stem, StemView stemView) {
        int state = presenter.updateStemRecordState(stem);
        displayStemViewRecordState(stemView, state);
    }

    @Override
    public void onStemRemove(StemModel stem, StemView stemView) {
        presenter.deleteStem(stem);
    }
}
