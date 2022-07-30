package com.example.periodcalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotesRecViewAdapter extends RecyclerView.Adapter<NotesRecViewAdapter.ViewHolder> {
    private ArrayList<Note> notes = new ArrayList<>();
    private Context context;
    private Util util;

    public NotesRecViewAdapter(Context context) {
        this.context = context;
        util = new Util();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_note_rec_view, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNoteDate.setText(notes.get(position).getDate());
        holder.txtNoteName.setText(notes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNoteDate;
        private TextView txtNoteName;

        public ViewHolder(View itemView) {
            super(itemView);

            txtNoteDate = (TextView) itemView.findViewById(R.id.txtNoteDate);
            txtNoteName = (TextView) itemView.findViewById(R.id.txtNoteName);
        }
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }
}
