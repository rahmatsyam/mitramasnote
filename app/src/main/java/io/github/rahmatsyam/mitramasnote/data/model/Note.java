package io.github.rahmatsyam.mitramasnote.data.model;


public class Note {


    private String aktivitas;
    private String date;
    private int id;


    public Note(int id, String aktivitas, String date) {

        this.aktivitas = aktivitas;
        this.date = date;
        this.id = id;


    }



    public String getAktivitas() {
        return aktivitas;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

}
