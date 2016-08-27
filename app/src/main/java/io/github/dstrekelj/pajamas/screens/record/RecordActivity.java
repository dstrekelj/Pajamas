package io.github.dstrekelj.pajamas.screens.record;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dstrekelj.pajamas.R;
import io.github.dstrekelj.pajamas.data.PajamasDataRepository;
import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.recorder.RecordingSession;
import io.github.dstrekelj.pajamas.screens.about.AboutActivity;
import io.github.dstrekelj.pajamas.screens.record.adapters.StemItemsAdapter;
import io.github.dstrekelj.pajamas.screens.record.impl.StemItemsAdapterListenerImpl;
import io.github.dstrekelj.pajamas.screens.record.impl.TrackTitleTextWatcher;

/**
 * The Record screen is where track recording occurs. A new recording session is initialised,
 * marking the recording of a new track. Stems are added, recorded, played, and / or removed. The
 * track can be played back and finalized into a WAV file on external storage media.
 */
public class RecordActivity extends AppCompatActivity implements RecordContract.View {
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

    private ProgressDialog progressDialog;
    private RecordContract.Presenter presenter;
    private StemItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        ButterKnife.bind(this);

        adapter = new StemItemsAdapter(this);
        presenter = new RecordPresenter(
                this,
                PajamasDataRepository.getInstance(this),
                getResources().getString(R.string.default_track_title),
                getResources().getString(R.string.default_stem_title)
        );

        adapter.setListener(new StemItemsAdapterListenerImpl(this, presenter));
        rvStems.setAdapter(adapter);
        rvStems.setLayoutManager(new LinearLayoutManager(this));

        etTrackTitle.addTextChangedListener(new TrackTitleTextWatcher(presenter));

        presenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_record, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_record_item_about:
                goToAboutScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    * LIFECYCLE METHODS
    * */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();

        adapter = null;
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
    public void displayAlertDialog(int titleResourceId, int messageResourceId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getResources().getString(titleResourceId));
        alertDialogBuilder.setMessage(getResources().getString(messageResourceId));
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialogBuilder.create().show();
    }

    @Override
    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    @Override
    public void displayProgressDialog(int titleResourceId, int messageResourceId) {
        progressDialog = ProgressDialog.show(
                this,
                getResources().getString(titleResourceId),
                getResources().getString(messageResourceId)
        );
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
    public void displayToast(String message) {
        Toast.makeText(
                getApplicationContext(),
                message,
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void displayToast(int stringResourceId) {
        Toast.makeText(
                getApplicationContext(),
                getResources().getString(stringResourceId),
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void displayTrackTitle(String title) {
        etTrackTitle.setText(title);
    }

    @Override
    public void goToAboutScreen() {
        startActivity(new Intent(this, AboutActivity.class));
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
        int state = presenter.updateTrackPlayerState();
        switch (state) {
            case RecordingSession.STATE_ACTIVE:
                btnPlayTrack.setText(R.string.stem_stop);
                btnFinalizeTrack.setEnabled(false);
                break;
            case RecordingSession.STATE_INACTIVE:
                btnPlayTrack.setText(R.string.stem_play);
                btnFinalizeTrack.setEnabled(true);
                break;
            default:
        }
    }
}
