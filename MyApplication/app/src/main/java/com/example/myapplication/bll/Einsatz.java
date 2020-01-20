package com.example.myapplication.bll;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;

public class Einsatz {


    private int id ;
    private int id_einsatzcode ;
    private int id_einsatzart ;
    private String titel;
    private String kurzbeschreibung;
    private String adresse ;
    private int plz;
    private Timestamp zeit;

    public Einsatz(int id, int id_einsatzcode, int id_einsatzart, String titel, String kurzbeschreibung, String adresse, int plz, Timestamp zeit) {
        this.id = id;
        this.id_einsatzcode = id_einsatzcode;
        this.id_einsatzart = id_einsatzart;
        this.titel = titel;
        this.kurzbeschreibung = kurzbeschreibung;
        this.adresse = adresse;
        this.plz = plz;
        this.zeit = zeit;
    }

    public Einsatz() {
    }

    public String getAddress(){
        return this.adresse + " " + this.plz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_einsatzcode() {
        return id_einsatzcode;
    }

    public void setId_einsatzcode(int id_einsatzcode) {
        this.id_einsatzcode = id_einsatzcode;
    }

    public int getId_einsatzart() {
        return id_einsatzart;
    }

    public void setId_einsatzart(int id_einsatzart) {
        this.id_einsatzart = id_einsatzart;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getKurzbeschreibung() {
        return kurzbeschreibung;
    }

    public void setKurzbeschreibung(String kurzbeschreibung) {
        this.kurzbeschreibung = kurzbeschreibung;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public Timestamp getZeit() {
        return zeit;
    }

    public void setZeit(Timestamp zeit) {
        this.zeit = zeit;
    }
}
