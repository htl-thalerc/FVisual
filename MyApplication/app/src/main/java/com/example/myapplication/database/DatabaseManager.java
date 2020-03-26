package com.example.myapplication.database;

import com.example.myapplication.bll.Einsatz;
import com.example.myapplication.bll.Einsatzart;
import com.example.myapplication.bll.Mitglied;
import com.example.myapplication.bll.Stuetzpunkt;
import com.example.myapplication.service.ServiceGetEinsaetzeFromMitgliedList;
import com.example.myapplication.service.ServiceGetEinsatzarten;
import com.example.myapplication.service.ServiceGetMitgliederList;
import com.example.myapplication.service.ServiceGetStuetzpunkt;
import com.example.myapplication.service.ServicePutMitglied;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DatabaseManager {
    private static DatabaseManager db = null;
    private static String ipHost = "http://192.168.8.121:3030";

    private DatabaseManager() {
    }

    public static DatabaseManager newInstance() {
        if (db == null) {
            db = new DatabaseManager();
        }
        return db;
    }

    public ArrayList<Mitglied> getAllMitglieder() {
        Gson gson = new Gson();
        ArrayList<Mitglied> retArtikel = new ArrayList<>();

        //each call needs an new instance of async !!
        ServiceGetMitgliederList controller = new ServiceGetMitgliederList();
        ServiceGetMitgliederList.setIpHost(ipHost);

        controller.execute();
        String strFromWebService = null;
        try {
            strFromWebService = controller.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Type colltype = new TypeToken<ArrayList<Mitglied>>(){}.getType();
            retArtikel = gson.fromJson(strFromWebService,colltype);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return retArtikel;
    }

    public ArrayList<Einsatz> getAllEinsetzeFromMitglied(int id) throws Exception {
        Gson gson = new Gson();
        ArrayList<Einsatz> einsatzList;

        //each call needs an new instance of async !!
        ServiceGetEinsaetzeFromMitgliedList controller = new ServiceGetEinsaetzeFromMitgliedList();
        ServiceGetEinsaetzeFromMitgliedList.setIpHost(ipHost + "/mitglieder/"+id+"/einsaetze");

        controller.execute();
        String strFromWebService = controller.get();

        try {
            Type colltype = new TypeToken<ArrayList<Einsatz>>(){}.getType();
            einsatzList = gson.fromJson(strFromWebService,colltype);
        } catch (Exception ex) {
            throw new Exception(strFromWebService);
        }
        return einsatzList;
    }

    public Stuetzpunkt getStuetzpunkt(int stuetzpunkt) throws Exception {
        Gson gson = new Gson();
        ArrayList<Stuetzpunkt> stuetzpunktList;

        //each call needs an new instance of async !!
        ServiceGetStuetzpunkt controller = new ServiceGetStuetzpunkt();
        ServiceGetStuetzpunkt.setIpHost(ipHost + "/stuetzpunkte/" + stuetzpunkt);

        controller.execute();
        String strFromWebService = controller.get();

        try {
            Type colltype = new TypeToken<ArrayList<Stuetzpunkt>>(){}.getType();
            stuetzpunktList = gson.fromJson(strFromWebService,colltype);
        } catch (Exception ex) {
            throw new Exception(strFromWebService);
        }
        return stuetzpunktList.get(0);
    }

    public ArrayList<Einsatzart> getEinsatzart() throws Exception {
        Gson gson = new Gson();
        ArrayList<Einsatzart> einsatzartList;

        //each call needs an new instance of async !!
        ServiceGetEinsatzarten controller = new ServiceGetEinsatzarten();
        ServiceGetEinsatzarten.setIpHost(ipHost);

        controller.execute();
        String strFromWebService = controller.get();

        try {
            Type colltype = new TypeToken<ArrayList<Einsatzart>>(){}.getType();
            einsatzartList = gson.fromJson(strFromWebService,colltype);
        } catch (Exception ex) {
            throw new Exception(strFromWebService);
        }
        return einsatzartList;
    }

    public String UpdateMitglied(Mitglied mitglied) throws ExecutionException, InterruptedException {
        Gson gson = new Gson();
        ServicePutMitglied controller = new ServicePutMitglied();
        ServicePutMitglied.setIPHost(ipHost + "/mitglieder/"+mitglied.getId());
        controller.setMitglied(mitglied);
        controller.execute();
        return controller.get();
    }


}
