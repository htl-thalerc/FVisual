package com.example.myapplication.service;

import android.os.AsyncTask;

import com.example.myapplication.bll.Mitglied;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
    protected String doInBackground(String... commands) {
        BufferedWriter writer = null;
        Gson gson = new Gson();
        boolean isError = false;
        java.net.URL url = null;
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String content = null;


        try {
            url = new java.net.URL(this.ipHost);
            // Öffnen der Connection
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setChunkedStreamingMode(0);
            conn.addRequestProperty("Content-Type", "application/json");
            conn.addRequestProperty("Authorization", "53616c7465645f5fc70def69b8f6a43bb830eb4835c02344a798099ca5a5ace531e8254f6108f3058c233a5aae22e25f29edbee629ce7375b0424d3c5bd883c3");

            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
//            writeStream(out);

            InputStream in = new BufferedInputStream(conn.getInputStream());
  //          readStream(in);
        }catch(Exception ex){
        } finally {
            conn.disconnect();
        }

        return content;
    }
}