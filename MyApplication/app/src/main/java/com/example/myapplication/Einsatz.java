package com.example.myapplication;


public class Einsatz {
    private int id ;
    private String einsatzart ;
    private String einsatzcode ;
    private String einsatzort ;
    private String titel;
    private String kurzbeschreibung;
    private String datum;
    private String zeit;
    private String feuerwehren;

    public Einsatz(int id, String einsatzart, String einsatzcode, String einsatzort, String titel, String kurzbeschreibung, String datum, String zeit, String feuerwehren) {
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
}
