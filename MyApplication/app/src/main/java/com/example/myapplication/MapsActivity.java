package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.bll.Einsatz;
import com.example.myapplication.bll.Einsatzart;
import com.example.myapplication.bll.Mitglied;
import com.example.myapplication.bll.Stuetzpunkt;
import com.example.myapplication.database.DatabaseManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, Serializable {
    GoogleMap googleMap;
    Mitglied currentMitglied;
    Stuetzpunkt currentStuetzpunkt;
    ArrayList<Einsatz> einsatzList;
    ArrayList<Einsatzart> einsatzartList;
    Geocoder gc;
    CheckBox chkBoxStuetzpunkt;
    CheckBox chkBoxEinsatz;
    DatabaseManager db;
    Einsatz currentEinsatz;
    Button editProfile;
    Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            currentMitglied = (Mitglied) getIntent().getSerializableExtra("serialzable");
            db = DatabaseManager.newInstance();
            einsatzList = new ArrayList<Einsatz>();
            einsatzartList = db.getEinsatzart();

            currentStuetzpunkt = db.getStuetzpunkt(currentMitglied.getStuetzpunkt());
            getEinsaetzeFromMitglied();

            chkBoxStuetzpunkt = findViewById(R.id.radioButtonStuetzpunktFilter);
            chkBoxEinsatz = findViewById(R.id.radioButtonEinsatzFilter);
            editProfile = findViewById(R.id.buttonEdit);
            logout = findViewById(R.id.logout);

            chkBoxEinsatz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    googleMap.clear();
                    checkBoxes(isChecked, chkBoxStuetzpunkt.isChecked());
                }
            });

            chkBoxStuetzpunkt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    googleMap.clear();
                    checkBoxes(chkBoxEinsatz.isChecked(), isChecked);
                }
            });

            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent send = new Intent(MapsActivity.this, EditActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("serialzable", currentMitglied);
                    send.putExtras(b);
                    startActivity(send);
                    finish();
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent send = new Intent(MapsActivity.this, LoginActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("serialzable", "logout");
                    send.putExtras(b);
                    startActivity(send);
                    finish();
                }
            });

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);

            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkBoxes(boolean isCheckedEinsatz, boolean isCheckedStuetzpunkt) {
        if(isCheckedEinsatz && isCheckedStuetzpunkt){
            showEinsaetze();
            showStuetzpunkt();
        }else if(!isCheckedEinsatz && isCheckedStuetzpunkt){
            showStuetzpunkt();
        }else if(isCheckedEinsatz && !isCheckedStuetzpunkt){
            showEinsaetze();
        }else if(!isCheckedEinsatz && !isCheckedStuetzpunkt){
            googleMap.clear();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        gc = new Geocoder(this);
        googleMap.setOnMarkerClickListener(this);
        try {
            CameraPosition cameraPosition =
                    new CameraPosition.Builder().target(
                            new LatLng(
                                    gc.getFromLocationName("St. Peter 47 9800 Spittal/Drau", 1).get(0).getLatitude(),
                                    gc.getFromLocationName("St. Peter 47 9800 Spittal/Drau", 1).get(0).getLongitude()
                            ))
                            .zoom(14f).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.moveCamera(cameraUpdate);
        }catch(Exception e){

        }
        showStuetzpunkt();
        showEinsaetze();
    }

    private void showEinsaetze() {
        for (Einsatz einsatz : einsatzList){
            Einsatzart currentEinsatzart = einsatzartList.get(einsatz.getId_einsatzart());
            List<Address> list = null;
            try {
                list = gc.getFromLocationName(einsatz.getAddress(), 1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Address add = list.get(0);
            switch (einsatz.getId_einsatzart()){
                /*
                1	Verkehrsunfall
                2	Brandeinsatz
                3	Technischer Einsatz
                4	Hilfeleistung
                5	Technische Hilfeleistung
                6	Personensuche
                7	Brandmeldealarm
                8	Wespen / Hornissen / Bienen
                 */

                //Verkehrsunfall
                case 1:
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(add.getLatitude(), add.getLongitude()))
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_sports_car))
                            .title(currentEinsatzart.getBeschreibung())
                            .snippet(String.valueOf(einsatz.getId()))
                    );
                    break;
                //Brandeinsatz
                case 2:
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(add.getLatitude(), add.getLongitude()))
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_fire))
                            .title(currentEinsatzart.getBeschreibung())
                            .snippet(String.valueOf(einsatz.getId()))
                    );
                    break;
                //Technischer Einsatz
                case 3:
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(add.getLatitude(), add.getLongitude()))
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_tools_cross_settings_symbol_for_interface))
                            .title(currentEinsatzart.getBeschreibung())
                            .snippet(String.valueOf(einsatz.getId()))
                    );
                    break;
                //Hilfeleistung
                case 4:
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(add.getLatitude(), add.getLongitude()))
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_customer_service))
                            .title(currentEinsatzart.getBeschreibung())
                            .snippet(String.valueOf(einsatz.getId()))
                    );
                    break;
                //Personensuche
                case 6:
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(add.getLatitude(), add.getLongitude()))
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_man_user))
                            .title(currentEinsatzart.getBeschreibung())
                            .snippet(String.valueOf(einsatz.getId()))
                    );
                    break;
                //Brandmeldealarm
                case 7:
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(add.getLatitude(), add.getLongitude()))
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_alarm))
                            .title(currentEinsatzart.getBeschreibung())
                            .snippet(String.valueOf(einsatz.getId()))
                    );
                    break;
                //Wespen / Hornissen / Bienen
                case 8:
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(add.getLatitude(), add.getLongitude()))
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_wasp))
                            .title(currentEinsatzart.getBeschreibung())
                            .snippet(String.valueOf(einsatz.getId()))
                    );
                    break;
            }

            }


    }

    private void showStuetzpunkt() {
        try {
            List<Address> list = gc.getFromLocationName(currentStuetzpunkt.getAddress(), 1);
            Address add = list.get(0);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(add.getLatitude(), add.getLongitude()))
                    .icon(bitmapDescriptorFromVector(this, R.drawable.ic_home_black_24dp))
                    .title(Stuetzpunkt.class.getName())
                    .snippet(Stuetzpunkt.class.getName())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getEinsaetzeFromMitglied() throws Exception {
        einsatzList.addAll(db.getAllEinsetzeFromMitglied(currentMitglied.getId()));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getSnippet().equals(new Stuetzpunkt().getClass().getName())) {
            showPopupStuetzpunkt(this);
        } else {
            for(Einsatz e:einsatzList){
                if(e.getId() == Integer.parseInt(marker.getSnippet())){
                    currentEinsatz = e;
                }
            }
            showPopupEinsatz(this);
        }

        return true;
    }

    private void showPopupStuetzpunkt(final Activity context) {
        int popupWidth = 350;
        int popupHeight = 230;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_stuetzpunkt, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);
        // Clear the default translucent background
        //popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, 200, 700);

        // Getting a reference to Close button, and close the popup when clicked.
        TextView txtName = (TextView) layout.findViewById(R.id.txtName);
        TextView txtOrt = (TextView) layout.findViewById(R.id.txtOrt);
        TextView txtPLZ = (TextView) layout.findViewById(R.id.txtPLZ);
        TextView txtStrasse = (TextView) layout.findViewById(R.id.txtStrasse);

        txtName.setText("Name: " + currentStuetzpunkt.getName());
        txtOrt.setText("Ort: " + currentStuetzpunkt.getOrt());
        txtPLZ.setText("PLZ: " + currentStuetzpunkt.getPlz());
        txtStrasse.setText("Straße: " + currentStuetzpunkt.getStrasse() + " " + currentStuetzpunkt.getHausnr());
    }

    private void showPopupEinsatz(final Activity context) {
        int popupWidth = 350;
        int popupHeight = 350;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_einsatz, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        // Clear the default translucent background
        //popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, 200, 700);

        // Getting a reference to Close button, and close the popup when clicked.
        TextView txtEinsatzcode = (TextView) layout.findViewById(R.id.txtEinsatzcode);
        TextView txtEinsatzart = (TextView) layout.findViewById(R.id.txtEinsatzart);
        TextView txtTitel = (TextView) layout.findViewById(R.id.txtTitel);
        TextView txtKurzbeschreibung = (TextView) layout.findViewById(R.id.txtKurzbeschreibung);
        TextView txtAdresse = (TextView) layout.findViewById(R.id.txtAdresse);
        TextView txtPLZ = (TextView) layout.findViewById(R.id.txtPLZ);
        TextView txtZeit = (TextView) layout.findViewById(R.id.txtZeit);


        txtEinsatzcode.setText("Einsatzcode: " + currentEinsatz.getId_einsatzcode());
        txtEinsatzart.setText("Einsatzart: " + einsatzartList.get(currentEinsatz.getId_einsatzart() - 1).getBeschreibung());
        txtTitel.setText("Titel: " + currentEinsatz.getTitel());
        txtKurzbeschreibung.setText("Kurzbeschreibung: " + currentEinsatz.getKurzbeschreibung());
        txtAdresse.setText("Adresse: " + currentEinsatz.getAdresse());
        txtPLZ.setText("PLZ: " + currentEinsatz.getPlz());
        txtZeit.setText("Zeit: " + currentEinsatz.getZeit());
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_placeholder);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(10, 10, vectorDrawable.getIntrinsicWidth() + 10, vectorDrawable.getIntrinsicHeight() + 10);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}