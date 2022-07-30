package com.example.periodcalendar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";

    private CalendarView calendarView;
    private TextView txtSettingData;
    private ImageView ivAddNewNote;
    private RecyclerView noteRecycleView;
    private Calendar calendar = Calendar.getInstance();


    private int tappedDay =  calendar.get(Calendar.DAY_OF_MONTH);
    private int tappedMonth = calendar.get(Calendar.MONTH) + 1;
    private int tappedYear = calendar.get(Calendar.YEAR);
    private String tappedDate = tappedDay + "-"+ tappedMonth + "-" + tappedYear;

    private int startingDay;
    private int startingMonth;
    private int startingYear;
    private int periodLength;
    private int cycleLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidgets();
        initRecView();
        getDateFromSetting();
        onSetListeners();

    }

    private void initWidgets() {
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        txtSettingData = (TextView) findViewById(R.id.txtSettingData);
        ivAddNewNote = (ImageView) findViewById(R.id.ivAddNewNote);
        noteRecycleView = (RecyclerView) findViewById(R.id.noteRecycleView);

    }

    private void initRecView() {
        NotesRecViewAdapter adapter = new NotesRecViewAdapter(this);
        noteRecycleView.setAdapter(adapter);
        noteRecycleView.setLayoutManager(new LinearLayoutManager(this));

        Util util = new Util();
        ArrayList<Note> notes = new ArrayList<>();
        ArrayList<Note> tappedDatenotes = new ArrayList<>();
        notes = util.getAllNotes();

        for(Note note: notes) {
            if(note.getDate().equals(tappedDate)){
                tappedDatenotes.add(note);
            }
        }

        adapter.setNotes(tappedDatenotes);
    }
    
    private void getDateFromSetting() {
        startingDay = SettingActivity.getStartingDay();
        startingMonth = SettingActivity.getStartingMonth();
        startingYear = SettingActivity.getStartingYear();

        periodLength = SettingActivity.getPeriodLength();
        cycleLength = SettingActivity.getCycleLength();

        txtSettingData.setText("Period Length: " + periodLength + "\nCycleLength: " + cycleLength + "\nstarting date: " + startingDay + "-" + startingMonth + "-" + startingYear);

//        calendarView.setSelectedWeekBackgroundColor(getResources().getColor(R.color.purple_200));
    }

    private void onSetListeners() {

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                tappedDay = i2;
                tappedMonth = i1 + 1;
                tappedYear = i;
                tappedDate = tappedDay + "-"+ tappedMonth + "-" + tappedYear;

                initRecView();
            }
        });

        ivAddNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this , AddNoteActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("day", tappedDay);
                bundle.putInt("month", tappedMonth);
                bundle.putInt("year", tappedYear);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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