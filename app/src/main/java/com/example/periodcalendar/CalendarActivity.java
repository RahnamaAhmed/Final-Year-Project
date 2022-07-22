package com.example.periodcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";

    private CalendarView calendarView;
    private TextView dateDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        dateDetail = (TextView) findViewById(R.id.dateDetail);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                int day = i2;
                int month = i1 + 1;
                int year = i;

                dateDetail.setText("Day: " + day+ "\nmonth: " + month + "\nyear:" + year);

                Toast.makeText(CalendarActivity.this,
                        "Day: " + day+ "\nmonth: " + month + "\nyear:" + year,
                        Toast.LENGTH_SHORT).show();
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