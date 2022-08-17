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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private Util util;


    private int tappedDay =  calendar.get(Calendar.DAY_OF_MONTH);
    private int tappedMonth = calendar.get(Calendar.MONTH) + 1;
    private int tappedYear = calendar.get(Calendar.YEAR);
    private String tappedDate = tappedDay + "-"+ tappedMonth + "-" + tappedYear;

    private int startingDay;
    private int startingMonth;
    private int startingYear;
    private int periodLength;
    private int cycleLength;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidgets();
        initRecView();
        getDate();
        onSetListeners();

    }

    private void initWidgets() {
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        txtSettingData = (TextView) findViewById(R.id.txtSettingData);
        ivAddNewNote = (ImageView) findViewById(R.id.ivAddNewNote);
        noteRecycleView = (RecyclerView) findViewById(R.id.noteRecycleView);
        util = new Util();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getDate() {
        startingDay = util.getPeriodStartingDay();
        startingMonth = util.getPeriodStartingMonth();
        startingYear = util.getPeriodStartingYear();

        periodLength = util.getPeriodLength();
        cycleLength = util.getCycleLength();


        setPeriodDatesOfAllMonths();


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setPeriodDatesOfAllMonths() {
        StringBuilder allPeriodDays = new StringBuilder();
        LocalDate localDate = LocalDate.of(startingYear, startingMonth, startingDay);
        LocalDate periodStartingLocalDate = LocalDate.of(startingYear, startingMonth, startingDay);

        ArrayList<String> allPeriods = new ArrayList<>();

        int monthsLeft = 12;

        while(monthsLeft > 0) {
            periodStartingLocalDate = localDate;

            for (int i = 1 ; i <= periodLength; i++) {
                allPeriods.add(localDate.toString());
                if(i==1 || i == periodLength) {
                    allPeriodDays.append(localDate);

                    if(i==1){
                        allPeriodDays.append(" To ");
                    }
                }

                if(i != periodLength) {
                    localDate = localDate.plusDays(1);
                }
            }

            allPeriodDays.append("\n\n");
            localDate = periodStartingLocalDate.plusDays(cycleLength);

            monthsLeft--;
        }

        txtSettingData.setText("Period Length: " + periodLength + "\nCycleLength: " + cycleLength +
                "\nstarting date: " + startingYear + "-" + startingMonth + "-" + startingDay +
                "\nPeriod Dates: \n" + allPeriodDays);
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