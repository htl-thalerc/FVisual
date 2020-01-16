package com.example.myapplication.service;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceGetEinsaetzeFromMitgliedList extends AsyncTask<String, Void, String> {
    private static final String URL = "";
    private static String ipHost = null;

    public static void setIpHost(String ip) {
        ipHost = ip;
    }
    @Override
    protected String doInBackground(String... command) {
        boolean isError = false;
        java.net.URL url = null;
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String content = null;

        try {
            url = new URL(ipHost);
            conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("Authorization", "53616c7465645f5fc70def69b8f6a43bb830eb4835c02344a798099ca5a5ace531e8254f6108f3058c233a5aae22e25f29edbee629ce7375b0424d3c5bd883c3");
            conn.addRequestProperty("metadata", "[{\"id\":\"ID\", \"id_einsatzcode\":\"ID_EINSATZCODE\" , \"id_einsatzart\": \"ID_EINSATZART\", \"titel\":\"TITEL\", \"kurzbeschreibung\":\"KURZBESCHREIBUNG\", \"adresse\": \"ADRESSE\", \"plz\": \"PLZ\", \"zeit\": \"ZEIT\"}]");

           /* if (!conn.getResponseMessage().contains("OK")) {
             //   isError = true;
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }*/
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            content = sb.toString();
            if (isError) {

            }
        } catch (Exception ex) {
            content = ex.getMessage();
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return content;

    }

}