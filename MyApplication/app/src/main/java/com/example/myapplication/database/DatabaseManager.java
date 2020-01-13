package com.example.myapplication.database;

import com.example.myapplication.bll.Einsatz;
import com.example.myapplication.bll.Mitglied;
import com.example.myapplication.bll.Stuetzpunkt;
import com.example.myapplication.service.ServiceGetEinsaetzeFromMitgliedList;
import com.example.myapplication.service.ServiceGetMitgliederList;
import com.example.myapplication.service.ServiceGetStuetzpunktList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DatabaseManager {
    private static DatabaseManager db = null;
    private static String ipHost = "http://192.168.8.111:3030";

    private DatabaseManager() {
    }

    public static DatabaseManager newInstance(String ip) {
        if (db == null) {
            db = new DatabaseManager();
        }
        ipHost = ip;
        return db;
    }

    public ArrayList<Mitglied> getAllMitglieder() throws Exception {
        Gson gson = new Gson();
        ArrayList<Mitglied> mitgliedList;

        //each call needs an new instance of async !!
        ServiceGetMitgliederList controller = new ServiceGetMitgliederList();
        ServiceGetMitgliederList.setIpHost(ipHost);

        controller.execute();
        String strFromWebService = controller.get();

        try {
            Type colltype = new TypeToken<ArrayList<Mitglied>>(){}.getType();
            mitgliedList = gson.fromJson(strFromWebService,colltype);
        } catch (Exception ex) {
            throw new Exception(strFromWebService);
        }
        return mitgliedList;
    }

    public ArrayList<Einsatz> getAllEinsetzeFromMitglied() throws Exception {
        Gson gson = new Gson();
        ArrayList<Einsatz> einsatzList;

        //each call needs an new instance of async !!
        ServiceGetEinsaetzeFromMitgliedList controller = new ServiceGetEinsaetzeFromMitgliedList();
        ServiceGetEinsaetzeFromMitgliedList.setIpHost(ipHost);

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

    public ArrayList<Stuetzpunkt> getAllStuetzpunkte() throws Exception {
        Gson gson = new Gson();
        ArrayList<Stuetzpunkt> stuetzpunktList;

        //each call needs an new instance of async !!
        ServiceGetStuetzpunktList controller = new ServiceGetStuetzpunktList();
        ServiceGetStuetzpunktList.setIpHost(ipHost);

        controller.execute();
        String strFromWebService = controller.get();

        try {
            Type colltype = new TypeToken<ArrayList<Stuetzpunkt>>(){}.getType();
            stuetzpunktList = gson.fromJson(strFromWebService,colltype);
        } catch (Exception ex) {
            throw new Exception(strFromWebService);
        }
        return stuetzpunktList;
    }
}
