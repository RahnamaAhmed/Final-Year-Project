package com.example.periodcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {

    private TextView txtNoteDate;
    private EditText etNoteName;
    private Button btnNoteAdd;
    private Calendar calendar = Calendar.getInstance();
    private Util util;

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

        util = new Util();
    }

    private void setDate(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        day = bundle.getInt("day", calendar.get(Calendar.DAY_OF_MONTH));
        month = bundle.getInt("month", calendar.get(Calendar.MONTH) + 1);
        year = bundle.getInt("year", calendar.get(Calendar.YEAR));

        txtNoteDate.setText(day + "-" + month +"-"+ year);
    }

    private void onSetListeners() {
        btnNoteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = day + "-" + month +"-"+ year;
                util.SetNote(date, etNoteName.getText().toString());

                Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
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