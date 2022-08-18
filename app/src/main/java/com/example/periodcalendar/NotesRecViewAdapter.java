package com.example.periodcalendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class NotesRecViewAdapter extends RecyclerView.Adapter<NotesRecViewAdapter.ViewHolder> {
    private ArrayList<Note> allNotes = new ArrayList<>();
    private Context context;
    private Util util;
    private SharedPreferences sharedPreferences;
    private Gson gson;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtNoteDate.setText(allNotes.get(position).getDate());
        holder.txtNoteName.setText(allNotes.get(position).getName());

        holder.noteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "HI", Toast.LENGTH_SHORT).show();
            }
        });

        holder.noteCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final Note note = allNotes.get(position);

                 new AlertDialog.Builder(context)
                        .setTitle("Deleting " + note.getName())
                        .setMessage("Are you sure you want to delete " + note.getName() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                allNotes.remove(note.getId());
                                updateNotes();

                                notifyDataSetChanged();

                                Toast.makeText(context, note.getName() + " Has successfully deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { }
                        })
                        .create()
                        .show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNoteDate;
        private TextView txtNoteName;
        private CardView noteCard;

        public ViewHolder(View itemView) {
            super(itemView);

            txtNoteDate = (TextView) itemView.findViewById(R.id.txtNoteDate);
            txtNoteName = (TextView) itemView.findViewById(R.id.txtNoteName);
            noteCard = (CardView) itemView.findViewById(R.id.noteCard);
        }
    }

    private void updateNotes() {
        sharedPreferences = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        gson = new Gson();

        String allNotesJson = gson.toJson(allNotes);

        editor.putString("allNotes", allNotesJson);
        editor.apply();
    }

    public void setNotes(ArrayList<Note> notes) {
        this.allNotes = notes;
        notifyDataSetChanged();
    }
}
