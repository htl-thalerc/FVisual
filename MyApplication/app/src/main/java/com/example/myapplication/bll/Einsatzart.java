package com.example.myapplication.bll;

public class Einsatzart {
    private int id;
    private String beschreibung;

    public Einsatzart(int id, String beschreibung) {
        this.id = id;
        this.beschreibung = beschreibung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }


}
