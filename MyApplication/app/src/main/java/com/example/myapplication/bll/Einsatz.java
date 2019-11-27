package com.example.myapplication.bll;


import com.example.myapplication.bll.Einsatzart;

public class Einsatz {
    private int id ;
    private Einsatzart einsatzart ;
    private String einsatzcode ;
    private String einsatzort ;
    private String titel;
    private String kurzbeschreibung;
    private String datum;
    private String zeit;
    private String feuerwehren;

    public Einsatz(int id, Einsatzart einsatzart, String einsatzcode, String einsatzort, String titel, String kurzbeschreibung, String datum, String zeit, String feuerwehren) {
        this.id = id;
        this.einsatzart = einsatzart;
        this.einsatzcode = einsatzcode;
        this.einsatzort = einsatzort;
        this.titel = titel;
        this.kurzbeschreibung = kurzbeschreibung;
        this.datum = datum;
        this.zeit = zeit;
        this.feuerwehren = feuerwehren;
    }

    public Einsatz() {

    }
}
