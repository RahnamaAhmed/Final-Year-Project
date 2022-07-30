package com.example.periodcalendar;

public class Note {
    private int id;
    private String date;
    private String name;

    public Note(int id, String date, String name) {
        this.id = id;
        this.date = date;
        this.name = name;
    }

    public Note(String date, String description) {
        this.date = date;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
