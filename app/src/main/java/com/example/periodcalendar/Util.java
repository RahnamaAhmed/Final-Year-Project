package com.example.periodcalendar;

import java.util.ArrayList;
import java.util.Calendar;

public class Util {
    private static Calendar calendar = Calendar.getInstance();

    private static ArrayList<Note> allNotes;
    private static int periodStartingDay = calendar.get(Calendar.DAY_OF_MONTH);
    private static int periodStartingMonth = calendar.get(Calendar.MONTH) + 1;
    private static int periodStartingYear = calendar.get(Calendar.YEAR);
    private static int periodLength = 5;
    private static int cycleLength = 28;

    private static int id = 0;

    public Util() {
        if (null == allNotes) {
            allNotes = new ArrayList<>();
        }
    }

    public ArrayList<Note> getAllNotes() {
        return allNotes;
    }

    public void SetNote(String date, String name) {
        id++;
        allNotes.add(new Note(id, date, name));
    }

    public boolean removeNote (Note note) {
        return allNotes.remove(note);
    }

    public void setPeriodLength(int periodLength){
        Util.periodLength = periodLength;
    }

    public int getPeriodLength(){
        return periodLength;
    }

    public void setCycleLength(int cycleLength){
        Util.cycleLength = cycleLength;
    }

    public int getCycleLength(){
        return cycleLength;
    }

    public void setPeriodStartingDay(int periodStartingDay){
        Util.periodStartingDay = periodStartingDay;
    }

    public int getPeriodStartingDay(){
        return periodStartingDay;
    }

    public void setPeriodStartingMonth(int periodStartingMonth){
        Util.periodStartingMonth = periodStartingMonth;
    }

    public int getPeriodStartingMonth(){
        return periodStartingMonth;
    }
        public void setPeriodStartingYear(int periodStartingYear){
        Util.periodStartingYear = periodStartingYear;
    }

    public int getPeriodStartingYear(){
        return periodStartingYear;
    }
}
