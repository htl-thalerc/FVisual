package com.example.myapplication.bll;

import java.util.ArrayList;

public class Stuetzpunkt {
    private int id;
    private String name;
    private String ort;
    private int plz;
    private String strasse;
    private String hausnr;

    public Stuetzpunkt(int id, String name, String ort, int plz, String strasse, String hausnr) {
        this.id = id;
        this.name = name;
        this.ort = ort;
        this.plz = plz;
        this.strasse = strasse;
        this.hausnr = hausnr;
    }

    public Stuetzpunkt() {

    }

    public String getAddress(){
        return this.ort + " " + this.plz + " " + this.strasse + " " + this.hausnr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausnr() {
        return hausnr;
    }

    public void setHausnr(String hausnr) {
        this.hausnr = hausnr;
    }
}
