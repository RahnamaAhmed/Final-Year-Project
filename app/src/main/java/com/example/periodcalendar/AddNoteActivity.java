package com.example.periodcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {

    private Util util;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private TextView txtNoteDate;
    private EditText etNoteName;
    private Button btnNoteAdd;
    private Calendar calendar = Calendar.getInstance();

    private ArrayList<Note> allNotes;
    private String allNotesJson;
    private int day;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidgets();
        setDate();
        onSetListeners();
    }

    private void initWidgets() {
        txtNoteDate = (TextView) findViewById(R.id.txtNoteDate);
        etNoteName = (EditText) findViewById(R.id.etNoteName);
        btnNoteAdd = (Button) findViewById(R.id.btnNoteAdd);

//        util = new Util();
    }

    private void setDate(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        day = bundle.getInt("day", calendar.get(Calendar.DAY_OF_MONTH));
        month = bundle.getInt("month", calendar.get(Calendar.MONTH) + 1);
        year = bundle.getInt("year", calendar.get(Calendar.YEAR));

        txtNoteDate.setText(year + "-" + month +"-"+ day);
    }

    private void onSetListeners() {
        btnNoteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = year + "-" + month +"-"+ day;
//                util.SetNote(date, etNoteName.getText().toString());

                laodAllNotes();
                addNote(date, etNoteName.getText().toString());
                gotoMainActivity();
            }
        });
    }

    private void laodAllNotes() {
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        gson = new Gson();

        Type type = new TypeToken<ArrayList<Note>>(){}.getType();
        allNotesJson = sharedPreferences.getString("allNotes", null);
        allNotes = gson.fromJson(allNotesJson, type);

        if(allNotes == null){
            allNotes = new ArrayList<>();
        }
    }

    private void addNote(String date, String noteName) {
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        gson = new Gson();

        allNotes.add(new Note(allNotes.size(), date, noteName));
        allNotesJson = gson.toJson(allNotes);

        editor.putString("allNotes", allNotesJson);
        editor.apply();
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
        startActivity(intent);
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