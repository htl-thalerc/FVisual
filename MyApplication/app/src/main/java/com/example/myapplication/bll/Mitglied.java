package com.example.myapplication.bll;

import java.util.ArrayList;

public class Mitglied {
    private int id;
    private Dienstgrad dienstgrad;
    private String vorname;
    private String nachname;
    private Stuetzpunkt stuetzpunkt;
    private String username;
    private String password;

    public Mitglied(int id, Dienstgrad dienstgrad, String vorname, String nachname, Stuetzpunkt stuetzpunkt, String username, String password) {
        this.id = id;
        this.dienstgrad = dienstgrad;
        this.vorname = vorname;
        this.nachname = nachname;
        this.stuetzpunkt = stuetzpunkt;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dienstgrad getDienstgrad() {
        return dienstgrad;
    }

    public void setDienstgrad(Dienstgrad dienstgrad) {
        this.dienstgrad = dienstgrad;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Stuetzpunkt getStuetzpunkt() {
        return stuetzpunkt;
    }

    public void setStuetzpunkt(Stuetzpunkt stuetzpunkt) {
        this.stuetzpunkt = stuetzpunkt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
