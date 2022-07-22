package com.example.periodcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    private TextView periodLengthTxt;
    private TextView cycleLengthTxt;

    private Spinner periodLengthSpinner;
    private Spinner cycleLengthSpinner;

    private ArrayList<Integer> perodLengthDays;
    private ArrayList<Integer> cycleLengthDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initWidgets();

        setPeriodSpinner();
        setCycleSpinner();


    }

    private void initWidgets(){
        periodLengthTxt = (TextView) findViewById(R.id.periodLengthTxt);
        cycleLengthTxt = (TextView) findViewById(R.id.cycleLengthTxt);

        periodLengthSpinner = (Spinner) findViewById(R.id.periodLengthSpinner);
        cycleLengthSpinner = (Spinner) findViewById(R.id.cycleLengthSpinner);
    }

    private void setPeriodSpinner (){
        perodLengthDays = new ArrayList<>();

        for (int i=1; i<=10; i++) {
            perodLengthDays.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item,
                perodLengthDays);

        periodLengthSpinner.setAdapter(adapter);
    }

    private void setCycleSpinner (){
        cycleLengthDays = new ArrayList<>();

        for (int i=1; i<=35; i++) {
            cycleLengthDays.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item,
                cycleLengthDays);

        cycleLengthSpinner.setAdapter(adapter);
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