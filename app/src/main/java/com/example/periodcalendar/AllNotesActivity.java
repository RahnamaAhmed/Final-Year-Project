package com.example.periodcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AllNotesActivity extends AppCompatActivity {

    private RecyclerView noteRecycleView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_notes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidgets();
    }

    private void initWidgets() {
        noteRecycleView = (RecyclerView) findViewById(R.id.noteRecycleView);

        NotesRecViewAdapter adapter = new NotesRecViewAdapter(this);
        noteRecycleView.setAdapter(adapter);
        noteRecycleView.setLayoutManager(new LinearLayoutManager(this));

        Util util = new Util();
        ArrayList<Note> allNotes = new ArrayList<>();
//        allNotes = util.getAllNotes();

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Gson gson = new Gson();
        String allNotesJson = sharedPreferences.getString("allNotes", null);

        Type type = new TypeToken<ArrayList<Note>>(){}.getType();
        allNotes = gson.fromJson(allNotesJson, type);

        if(allNotes == null){
            allNotes = new ArrayList<>();
        }

        adapter.setNotes(allNotes);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}