package io.github.dstrekelj.pajamas.screens.record.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dstrekelj.pajamas.R;
import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.recorder.RecordingSession;
import io.github.dstrekelj.pajamas.screens.record.adapters.StemItemsAdapter;
import io.github.dstrekelj.pajamas.screens.record.impl.StemTitleTextWatcher;

/**
 * Custom view for track stems.
 */
public class StemView extends CardView {
    public static final String TAG = "StemView";

    @BindView(R.id.view_record_stem_btn_play)
    Button btnPlay;
    @BindView(R.id.view_record_stem_btn_record)
    Button btnRecord;
    @BindView(R.id.view_record_stem_btn_remove)
    Button btnRemove;
    @BindView(R.id.view_record_stem_et_stem_title)
    EditText etStemTitle;

    private StemItemsAdapter.StemItemsAdapterListener stemItemsAdapterListener;
    private StemModel stem;

    public StemView(Context context) {
        super(context);
        initialize(context);
    }

    public StemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public StemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    public void setStemItemsAdapterListener(StemItemsAdapter.StemItemsAdapterListener stemItemsAdapterListener) {
        this.stemItemsAdapterListener = stemItemsAdapterListener;
    }

    public void setStem(StemModel stem) {
        this.stem = stem;

        if (stem.getBuffer() != null) {
            btnPlay.setEnabled(true);
        }

        etStemTitle.setText(this.stem.getTitle());
        etStemTitle.addTextChangedListener(new StemTitleTextWatcher(this.stem));
    }

    public void setStemPlayState(int recordingSessionState) {
        switch (recordingSessionState) {
            case RecordingSession.STATE_STEM_PLAYER_ACTIVE:
                btnPlay.setText(R.string.stem_stop);
                btnRecord.setEnabled(false);
                break;
            case RecordingSession.STATE_STEM_PLAYER_STOPPED:
                btnPlay.setText(R.string.stem_play);
                btnRecord.setEnabled(true);
                break;
            default:
        }
    }

    public void setStemRecordState(int recordingSessionState) {
        switch (recordingSessionState) {
            case RecordingSession.STATE_STEM_RECORDER_ACTIVE:
                btnRecord.setText(R.string.stem_stop);
                btnPlay.setEnabled(false);
                break;
            case RecordingSession.STATE_STEM_RECORDER_STOPPED:
                btnRecord.setText(R.string.stem_record);
                btnPlay.setEnabled(true);
                break;
            default:
        }
    }

    @OnClick(R.id.view_record_stem_btn_play) void onStemPlay() {
        if (isActive()) {
            stemItemsAdapterListener.onStemPlay(stem, this);
        }
    }

    @OnClick(R.id.view_record_stem_btn_record) void onStemRecord() {
        if (isActive()) {
            stemItemsAdapterListener.onStemRecord(stem, this);
        }
    }

    @OnClick(R.id.view_record_stem_btn_remove) void onStemRemove() {
        if (isActive()) {
            stemItemsAdapterListener.onStemRemove(stem, this);
        }
    }

    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_record_stem, this);
        ButterKnife.bind(this);

        btnPlay.setEnabled(false);
    }

    private boolean isActive() {
        return (stemItemsAdapterListener != null) && (stem != null);
    }
}
