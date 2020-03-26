package com.example.myapplication.service;

import android.os.AsyncTask;

import com.example.myapplication.bll.Mitglied;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;


public class ServicePutMitglied extends AsyncTask<String, Void, String> {

    private static final String URL ="";
    private static String ipHost = null;
    private Mitglied mitglied = null;


    public static void setIPHost( String ip){
        ipHost = ip;
    }
    public void setMitglied( Mitglied mitglied ){
        this.mitglied = mitglied;
    }
    @Override
    protected String doInBackground(String... command) {
        java.net.URL url = null;
        HttpURLConnection conn = null;
        BufferedWriter writer = null;
        BufferedReader reader = null;
        String content = null;
        Gson gson = new Gson();

        try {
            url = new java.net.URL(ipHost);
            conn = (HttpURLConnection)url.openConnection();
            conn.addRequestProperty("Authorization", "53616c7465645f5fc70def69b8f6a43bb830eb4835c02344a798099ca5a5ace531e8254f6108f3058c233a5aae22e25f29edbee629ce7375b0424d3c5bd883c3");
            conn.addRequestProperty("metadata", "[{\"id\":\"ID\", \"dienstgrad\":\"BEZEICHNUNG\" , \"id_stuetzpunkt\": \"ID_STUETZPUNKT\", \"vorname\":\"VORNAME\", \"nachname\":\"NACHNAME\", \"username\": \"USERNAME\", \"password\": \"PASSWORD\", \"isAdmin\": \"ISADMIN\"}]");

            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            writer = new BufferedWriter( new OutputStreamWriter(( conn.getOutputStream())));
            writer.write(gson.toJson(mitglied));
            writer.flush();
            writer.close();

            // Überprüfen, ob ein Fehler aufgetreten ist, lesen der Fehlermeldung
            if( conn.getResponseCode() != 200){
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine())!= null){
                    sb.append(line);
                }
                content = conn.getResponseCode() + " " + sb.toString();
            }
            else{
                content = "ResponseCode: "+conn.getResponseCode();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try{
                if( reader != null){
                    reader.close();

                }
                writer.close();
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }
}