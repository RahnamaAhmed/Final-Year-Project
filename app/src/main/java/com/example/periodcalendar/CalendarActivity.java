package com.example.periodcalendar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";

    private CalendarView calendarView;
    private TextView txtDateDetail;
    private TextView txtSettingData;
    private ImageView ivAddNewNote;

    private int day;
    private int month;
    private int year;
    private int periodLength;
    private int cycleLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidgets();
        getDateFromSetting();
        onSetListeners();

    }

    private void initWidgets() {
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        txtDateDetail = (TextView) findViewById(R.id.txtDateDetail);
        txtSettingData = (TextView) findViewById(R.id.txtSettingData);
        ivAddNewNote = (ImageView) findViewById(R.id.ivAddNewNote);
    }
    
    private void getDateFromSetting() {
        day = SettingActivity.getStartingDay();
        month = SettingActivity.getStartingMonth();
        year = SettingActivity.getStartingYear();

        periodLength = SettingActivity.getPeriodLength();
        cycleLength = SettingActivity.getCycleLength();

        txtSettingData.setText("Period Length: " + periodLength + "\nCycleLength: " + cycleLength + "\nstarting date: " + day + "-" + month + "-" + year);

//        calendarView.setSelectedWeekBackgroundColor(getResources().getColor(R.color.purple_200));
    }

    private void onSetListeners() {

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                int day = i2;
                int month = i1 + 1;
                int year = i;

//                Calendar selectedday = new GregorianCalendar(2022,Calendar.JULY , 29);
//                calendarView.setDate(selectedday.getTimeInMillis());
//                calendarView.setSelectedWeekBackgroundColor(getResources().getColor(R.color.purple_200));

                txtDateDetail.setText("tapped Date: " + day+ "-" + month + "-" + year + "\n\n");

                Toast.makeText(CalendarActivity.this,
                        "Day: " + day+ "\nmonth: " + month + "\nyear:" + year,
                        Toast.LENGTH_SHORT).show();
            }
        });

        ivAddNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this , AddNoteActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("day", day);
                bundle.putInt("month", month);
                bundle.putInt("year", year);

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