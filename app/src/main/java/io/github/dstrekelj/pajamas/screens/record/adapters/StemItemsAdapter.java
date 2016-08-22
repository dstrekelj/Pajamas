package io.github.dstrekelj.pajamas.screens.record.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dstrekelj.pajamas.R;
import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.screens.record.impl.StemTitleTextWatcher;

/**
 * TODO: Comment.
 */
public class StemItemsAdapter extends RecyclerView.Adapter<StemItemsAdapter.StemViewHolder> {
    public static final String TAG = "StemItemsAdapter";

    private Context context;
    private LayoutInflater inflater;
    private List<StemModel> items;
    private StemItemsAdapterListener listener;

    public StemItemsAdapter(Context context) {
        this.context = context;

        inflater = LayoutInflater.from(this.context);
        items = new ArrayList<>();
        listener = null;
    }

    public void setListener(StemItemsAdapterListener listener) {
        this.listener = listener;
    }

    public List<StemModel> getItems() {
        return items;
    }

    public void setItems(List<StemModel> items) {
        if (!items.isEmpty()) {
            this.items.clear();
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void insertItem(StemModel item) {
        items.add(item);
        notifyItemInserted(items.size());
    }

    public void removeItem(StemModel item) {
        int i;
        for (i = 0; i < items.size(); i++) {
            if (items.get(i).equals(item)) {
                items.remove(i);
                break;
            }
        }
        notifyItemRemoved(i);
    }

    public int getItemPosition(StemModel item) {
        int i;
        for (i = 0; i < items.size(); i++) {
            if (items.get(i).equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public StemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StemViewHolder(inflater.inflate(R.layout.item_record_stem, parent, false));
    }

    @Override
    public void onBindViewHolder(StemViewHolder holder, int position) {
        StemModel item = items.get(position);
        holder.etStemTitle.setText(item.getTitle());
        holder.etStemTitle.addTextChangedListener(new StemTitleTextWatcher(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface StemItemsAdapterListener {
        void onStemRecord(StemModel stem, Button button);
        void onStemPlay(StemModel stem, Button button);
        void onStemRemove(StemModel stem, Button button);
    }

    class StemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_record_stem_et_stem_title)
        EditText etStemTitle;
        @BindView(R.id.item_record_stem_btn_play)
        Button btnPlay;
        @BindView(R.id.item_record_stem_btn_record)
        Button btnRecord;
        @BindView(R.id.item_record_stem_btn_remove)
        Button btnRemove;

        public StemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_record_stem_btn_play) void onStemPlayPause() {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onStemPlay(items.get(position), btnPlay);
            }
        }

        @OnClick(R.id.item_record_stem_btn_record) void onStemRecord() {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onStemRecord(items.get(position), btnRecord);
            }
        }

        @OnClick(R.id.item_record_stem_btn_remove) void onStemRemove() {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onStemRemove(items.get(position), btnRemove);
            }
        }
    }
}
