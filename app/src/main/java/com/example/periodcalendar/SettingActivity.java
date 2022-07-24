package com.example.periodcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {

    private TextView periodLengthTxt;
    private TextView cycleLengthTxt;

    private Spinner periodLengthSpinner;
    private Spinner cycleLengthSpinner;
    private DatePicker simpleDatePickerStartingDate;
    private static Calendar calendar = Calendar.getInstance();

    private ArrayList<Integer> periodLengthDays;
    private ArrayList<Integer> cycleLengthDays;

    private static int periodLength = 5;
    private static int cycleLength = 28;

    private static int startingDay = calendar.get(Calendar.DAY_OF_MONTH);
    private static int startingMonth = calendar.get(Calendar.MONTH) + 1;
    private static int startingYear = calendar.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidgets();

        setPeriodSpinner();
        setCycleSpinner();

        setPeriodLength();
        setCycleLength();

        setDate();
    }

    private void initWidgets(){
        periodLengthTxt = (TextView) findViewById(R.id.periodLengthTxt);
        cycleLengthTxt = (TextView) findViewById(R.id.cycleLengthTxt);

        periodLengthSpinner = (Spinner) findViewById(R.id.periodLengthSpinner);
        cycleLengthSpinner = (Spinner) findViewById(R.id.cycleLengthSpinner);

        simpleDatePickerStartingDate = (DatePicker) findViewById(R.id.simpleDatePickerStartingDate);

    }

    private void setPeriodSpinner (){
        periodLengthDays = new ArrayList<>();

        for (int i=2; i<=10; i++) {
            periodLengthDays.add(i);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item,
                periodLengthDays);

        periodLengthSpinner.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(periodLength);
        periodLengthSpinner.setSelection(spinnerPosition);
    }

    private void setCycleSpinner (){
        cycleLengthDays = new ArrayList<>();

        for (int i=21; i<=40; i++) {
            cycleLengthDays.add(i);
        }
        
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item,
                cycleLengthDays);

        cycleLengthSpinner.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(cycleLength);
        cycleLengthSpinner.setSelection(spinnerPosition);
    }

    private void setPeriodLength () {
        periodLengthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                periodLength = periodLengthDays.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public static int getPeriodLength () {
        return periodLength;
    }

    private void setCycleLength () {
        cycleLengthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cycleLength = cycleLengthDays.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public static int getCycleLength () {
        return cycleLength;
    }

    private void setDate () {
        simpleDatePickerStartingDate.init(startingYear, startingMonth, startingDay, (datePicker, i, i1, i2) -> {
            startingDay = i2;
            startingMonth= i1;
            startingYear = i;
        });
    }

    public static int getStartingDay () {
        return startingDay;
    }

    public static int getStartingMonth () {
        return startingMonth;
    }

    public static int getStartingYear () {
        return startingYear;
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