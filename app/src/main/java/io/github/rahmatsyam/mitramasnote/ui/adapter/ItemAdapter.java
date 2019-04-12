package io.github.rahmatsyam.mitramasnote.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.rahmatsyam.mitramasnote.R;
import io.github.rahmatsyam.mitramasnote.data.model.Note;
import io.github.rahmatsyam.mitramasnote.ui.activity.MainActivity;
import io.github.rahmatsyam.mitramasnote.ui.util.EmptyRecyclerView;

public class ItemAdapter extends EmptyRecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Note> noteList;
    private Context mContext;

    public ItemAdapter(Context context, List<Note> mListNote) {
        this.noteList = mListNote;
        this.mContext = context;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_aktivitas, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder viewHolder, final int position) {
        final Note note = noteList.get(position);
        viewHolder.tvAktivtas.setText(note.getAktivitas());
        viewHolder.tvDate.setText(note.getDate());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvAktivtas;
        TextView tvDate;

        ItemViewHolder(View view) {
            super(view);
            tvAktivtas = view.findViewById(R.id.tv_aktivitas);
            tvDate = view.findViewById(R.id.tv_date);
        }
    }
}
