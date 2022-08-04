package com.example.periodcalendar;

import java.util.ArrayList;

public class Util {
    private static ArrayList<Note> allNotes;

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

    private void initAllBooks() {
    }
}
