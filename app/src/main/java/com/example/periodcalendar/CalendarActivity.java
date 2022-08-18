package com.example.periodcalendar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";

    private Util util;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private CalendarView calendarView;
    private TextView txtSettingData;
    private ImageView ivAddNewNote;
    private RecyclerView noteRecycleView;
    private Calendar calendar = Calendar.getInstance();

    private int tappedDay =  calendar.get(Calendar.DAY_OF_MONTH);
    private int tappedMonth = calendar.get(Calendar.MONTH) + 1;
    private int tappedYear = calendar.get(Calendar.YEAR);
    private String tappedDate = tappedYear + "-"+ tappedMonth + "-" + tappedDay;

    private int startingDay;
    private int startingMonth;
    private int startingYear;
    private int periodLength;
    private int cycleLength;
    private ArrayList<Note> allNotes;
    private String allNotesJson;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidgets();
        initRecView();
        getData();
        onSetListeners();
    }

    private void initWidgets() {
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        txtSettingData = (TextView) findViewById(R.id.txtSettingData);
        ivAddNewNote = (ImageView) findViewById(R.id.ivAddNewNote);
        noteRecycleView = (RecyclerView) findViewById(R.id.noteRecycleView);
//        util = new Util();
    }

    private void initRecView() {
        NotesRecViewAdapter adapter = new NotesRecViewAdapter(this);
        noteRecycleView.setAdapter(adapter);
        noteRecycleView.setLayoutManager(new LinearLayoutManager(this));

//        Util util = new Util();
//        allNotes = util.getAllNotes();

        ArrayList<Note> tappedDateNotes = new ArrayList<>();
        loadAllNotes();

        for(Note note: allNotes) {
            if(note.getDate().equals(tappedDate)){
                tappedDateNotes.add(note);
            }
        }

        adapter.setNotes(tappedDateNotes);
    }

    private void loadAllNotes() {
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        gson = new Gson();

        Type type = new TypeToken<ArrayList<Note>>(){}.getType();
        allNotesJson = sharedPreferences.getString("allNotes", null);
        allNotes = gson.fromJson(allNotesJson, type);

        if(allNotes == null){
            allNotes = new ArrayList<>();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getData() {
//        startingDay = util.getPeriodStartingDay();
//        startingMonth = util.getPeriodStartingMonth();
//        startingYear = util.getPeriodStartingYear();
//
//        periodLength = util.getPeriodLength();
//        cycleLength = util.getCycleLength();

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        periodLength = sharedPreferences.getInt("periodLength", 5);
        cycleLength = sharedPreferences.getInt("cycleLength", 28);

        startingDay = sharedPreferences.getInt("startingDay", calendar.get(Calendar.DAY_OF_MONTH));
        startingMonth = sharedPreferences.getInt("startingMonth", calendar.get(Calendar.MONTH) + 1);
        startingYear = sharedPreferences.getInt("startingYear", calendar.get(Calendar.YEAR));

        setPeriodDatesOfAllMonths();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setPeriodDatesOfAllMonths() {
        StringBuilder allPeriodDays = new StringBuilder();
        LocalDate localDate = LocalDate.of(startingYear, startingMonth, startingDay);
        LocalDate periodStartingLocalDate;

        ArrayList<String> allPeriods = new ArrayList<>();

        int monthsLeft = 12;

        while(monthsLeft > 0) {
            periodStartingLocalDate = localDate;

            for (int i = 1; i <= periodLength; i++) {
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

            allPeriodDays.append("\n");
            localDate = periodStartingLocalDate.plusDays(cycleLength);

            monthsLeft--;
        }

        txtSettingData.setText("Period starting date: " + startingYear + "-" + startingMonth + "-" + startingDay +
                "\nPeriod Dates: \n" + allPeriodDays);
    }

    private void onSetListeners() {

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                tappedDay = i2;
                tappedMonth = i1 + 1;
                tappedYear = i;
                tappedDate = tappedYear + "-"+ tappedMonth + "-" + tappedDay;

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