package io.github.dstrekelj.pajamas.screens.record.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dstrekelj.pajamas.R;
import io.github.dstrekelj.pajamas.models.StemModel;
import io.github.dstrekelj.pajamas.screens.record.impl.StemTitleTextWatcher;
import io.github.dstrekelj.pajamas.screens.record.views.StemView;

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
        holder.stmvStem.setStem(item);
        holder.stmvStem.setStemItemsAdapterListener(listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface StemItemsAdapterListener {
        void onStemRecord(StemModel stem, StemView stemView);
        void onStemPlay(StemModel stem, StemView stemView);
        void onStemRemove(StemModel stem, StemView stemView);
    }

    class StemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_record_stem_stmv_stem)
        StemView stmvStem;

        public StemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
