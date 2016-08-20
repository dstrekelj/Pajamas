package io.github.dstrekelj.pajamas.screens.record;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.dstrekelj.pajamas.R;
import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.screens.record.adapters.StemItemsAdapter;
import io.github.dstrekelj.pajamas.screens.record.impl.TrackTitleTextWatcher;

public class RecordActivity extends AppCompatActivity implements RecordContract.View, StemItemsAdapter.StemItemsAdapterListener {
    public static final String TAG = "RecordActivity";

    @BindView(R.id.activity_record_et_track_title)
    EditText etTrackTitle;
    @BindView(R.id.activity_record_fab_add_stem)
    FloatingActionButton fabAddStem;
    @BindView(R.id.activity_record_rv_stems)
    RecyclerView rvStems;

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
        etTrackTitle.addTextChangedListener(new TrackTitleTextWatcher(presenter));

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
    protected void onStart() {
        super.onStart();
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
    public void onStemRecord(StemModel stem) {
        presenter.updateStemRecordState(stem);
    }

    @Override
    public void onStemPlayPause(StemModel stem) {
        presenter.updateStemPlayPauseState(stem);
    }

    @Override
    public void onStemRemove(StemModel stem) {
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

    @OnClick(R.id.activity_record_fab_add_stem) void onClickFabAddStem() {
        presenter.createStem();
    }
}
