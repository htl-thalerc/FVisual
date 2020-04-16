package com.example.myapplication.bll;

import java.io.Serializable;

public class Mitglied implements Serializable {
    private int id;
    private int id_dienstgrad;
    private String vorname;
    private String nachname;
    private int id_stuetzpunkt;
    private String username;
    private String password;
    private String isAdmin;

    public Mitglied(int id, int id_dienstgrad, String vorname, String nachname, int stuetzpunkt, String username, String password, String isAdmin) {
        this.id = id;
        this.id_dienstgrad = id_dienstgrad;
        this.vorname = vorname;
        this.nachname = nachname;
        this.id_stuetzpunkt = stuetzpunkt;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDienstgrad() {
        return id_dienstgrad;
    }

    public void setDienstgrad(int dienstgrad) {
        this.id_dienstgrad = id_dienstgrad;
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

    public int getStuetzpunkt() {
        return id_stuetzpunkt;
    }

    public void setStuetzpunkt(int stuetzpunkt) {
        this.id_stuetzpunkt = stuetzpunkt;
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

    public String isAdmin() {
        return isAdmin;
    }

    public void setAdmin(String admin) {
        isAdmin = admin;
    }
}
