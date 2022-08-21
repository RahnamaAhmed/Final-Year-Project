package com.example.periodcalendar;

import static android.provider.Settings.System.DATE_FORMAT;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";

    private Util util;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    MaterialCalendarView calendarView;
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
      List<String> customPeriodDays = Arrays.asList(
            "2022-08-09","2022-08-10","2022-08-11","2022-08-12","2022-08-13");
      ArrayList<String> periodDaysList = new ArrayList<String>();

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
        calendarView =  findViewById(R.id.calendarView);
        txtSettingData = (TextView) findViewById(R.id.txtSettingData);
        ivAddNewNote = (ImageView) findViewById(R.id.ivAddNewNote);
        noteRecycleView = (RecyclerView) findViewById(R.id.noteRecycleView);
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        final LocalDate min = getLocalDate("2020-01-01");
        final  LocalDate max = getLocalDate("2024-12-30");
        calendarView.state().edit().setMinimumDate(min).setMaximumDate(max).commit();
    }

    private void initRecView() {
        NotesRecViewAdapter adapter = new NotesRecViewAdapter(this);
        noteRecycleView.setAdapter(adapter);
        noteRecycleView.setLayoutManager(new LinearLayoutManager(this));

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

        int monthsLeft = 6;

        while(monthsLeft > 0) {
            periodStartingLocalDate = localDate;

            for (int i = 1; i <= periodLength; i++) {
                allPeriods.add(localDate.toString());
                periodDaysList.add(localDate.toString());
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

        setEvent(periodDaysList, 1);
        calendarView.invalidateDecorators();
    }

    private void onSetListeners() {



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

    void setEvent(List<String> dateList, int color) {
        List<org.threeten.bp.LocalDate> localDateList = new ArrayList<>();

        for (String string : dateList) {
            org.threeten.bp.LocalDate calendar = getLocalDate(string);
            if (calendar != null) {
                localDateList.add(calendar);
            }
        }


        List<CalendarDay> datesLeft = new ArrayList<>();
        List<CalendarDay> datesCenter = new ArrayList<>();
        List<CalendarDay> datesRight = new ArrayList<>();
        List<CalendarDay> datesIndependent = new ArrayList<>();


        for (org.threeten.bp.LocalDate localDate : localDateList) {

            boolean right = false;
            boolean left = false;

            for (org.threeten.bp.LocalDate day1 : localDateList) {


                if (localDate.isEqual(day1.plusDays(1))) {
                    left = true;
                }
                if (day1.isEqual(localDate.plusDays(1))) {
                    right = true;
                }
            }

            if (left && right) {
                datesCenter.add(CalendarDay.from(localDate));
            } else if (left) {
                datesLeft.add(CalendarDay.from(localDate));
            } else if (right) {
                datesRight.add(CalendarDay.from(localDate));
            } else {
                datesIndependent.add(CalendarDay.from(localDate));
            }
        }

        if (color == 1) {
            setDecor(datesCenter, R.drawable.p_center);
            setDecor(datesLeft, R.drawable.p_left);
            setDecor(datesRight, R.drawable.p_right);
            setDecor(datesIndependent, R.drawable.p_independent);
        } else {
            setDecor(datesCenter, R.drawable.g_center);
            setDecor(datesLeft, R.drawable.g_left);
            setDecor(datesRight, R.drawable.g_right);
            setDecor(datesIndependent, R.drawable.g_independent);
        }
    }

    void setDecor(List<CalendarDay> calendarDayList, int drawable) {
        calendarView.addDecorators(new EventDecorator(CalendarActivity.this
                , drawable
                , calendarDayList));
    }

    LocalDate getLocalDate(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault());
       return LocalDate.parse(date, dtf);
    }
}