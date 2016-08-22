package io.github.dstrekelj.pajamas.screens.record.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dstrekelj.pajamas.R;
import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.screens.record.adapters.StemItemsAdapter;

/**
 * TODO: Comment.
 */
public class StemView extends CardView {
    public static final String TAG = "StemView";

    @BindView(R.id.view_record_stem_btn_play)
    public Button btnPlay;
    @BindView(R.id.view_record_stem_btn_record)
    public Button btnRecord;
    @BindView(R.id.view_record_stem_btn_remove)
    public Button btnRemove;
    @BindView(R.id.view_record_stem_et_stem_title)
    public EditText etStemTitle;

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
    }

    public void enable() {
        btnPlay.setClickable(true);
        btnRecord.setClickable(true);
        btnRemove.setClickable(true);
    }

    @OnClick(R.id.view_record_stem_btn_play) void onStemPlay() {
        Log.d(TAG, "onStemPlay");
        /*if (isActive()) {
            stemItemsAdapterListener.onStemPlay(stem, btnPlay);
        }*/
    }

    @OnClick(R.id.view_record_stem_btn_record) void onStemRecord() {
        Log.d(TAG, "onStemRecord");
        /*if (isActive()) {
            stemItemsAdapterListener.onStemRecord(stem, btnRecord);
        }*/
    }

    @OnClick(R.id.view_record_stem_btn_remove) void onStemRemove() {
        Log.d(TAG, "onStemRemove");
        /*if (isActive()) {
            stemItemsAdapterListener.onStemRemove(stem, btnRemove);
        }*/
    }

    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_record_stem, this);
        ButterKnife.bind(this);

        btnPlay.setClickable(false);
        btnRecord.setClickable(false);
        btnRemove.setClickable(false);
    }

    private boolean isActive() {
        return (stemItemsAdapterListener != null) && (stem != null);
    }
}
