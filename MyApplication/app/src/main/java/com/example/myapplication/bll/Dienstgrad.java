package com.example.myapplication.bll;

public class Dienstgrad {
    private int id;
    private String kuerzel;
    private String bezeichnung;

    public Dienstgrad(int id, String kuerzel, String bezeichnung) {
        this.id = id;
        this.kuerzel = kuerzel;
        this.bezeichnung = bezeichnung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKuerzel() {
        return kuerzel;
    }

    public void setKuerzel(String kuerzel) {
        this.kuerzel = kuerzel;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}
