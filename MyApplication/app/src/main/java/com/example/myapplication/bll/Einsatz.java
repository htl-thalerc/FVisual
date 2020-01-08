package com.example.myapplication.bll;

import java.util.Date;
import java.util.Timer;

public class Einsatz {
    private int id ;
    private Einsatzart einsatzart ;
    private Einsatzcode einsatzcode ;
    private String einsatzort ;
    private String titel;
    private String kurzbeschreibung;
    private Date datum;
    private Timer zeit;
    private int mannstaerke;
    //private String feuerwehren;

    public Einsatz(int id, Einsatzart einsatzart, Einsatzcode einsatzcode, String einsatzort, String titel, String kurzbeschreibung, Date datum, Timer zeit, String feuerwehren) {
        this.id = id;
        this.einsatzart = einsatzart;
        this.einsatzcode = einsatzcode;
        this.einsatzort = einsatzort;
        this.titel = titel;
        this.kurzbeschreibung = kurzbeschreibung;
        this.datum = datum;
        this.zeit = zeit;
        //this.feuerwehren = feuerwehren;
    }

    public Einsatz() {

    }
}
