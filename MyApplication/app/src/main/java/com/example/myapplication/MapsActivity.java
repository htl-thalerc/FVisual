package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.bll.Dienstgrad;
import com.example.myapplication.bll.Einsatz;
import com.example.myapplication.bll.Mitglied;
import com.example.myapplication.bll.Stuetzpunkt;
import com.example.myapplication.database.DatabaseManager;
import com.example.myapplication.service.ServiceGetEinsaetzeList;
import com.example.myapplication.service.ServiceGetMitgliederList;
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
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    GoogleMap googleMap;
    Mitglied currentMitglied;
    Stuetzpunkt currentStuetzpunkt;
    ArrayList<Einsatz> einsatzList;
    Geocoder gc;
    RadioButton radioButtonStuetzpunkt;
    RadioButton radioButtonEinsatz;
    RadioGroup radioGroupFilter;
    Button resetButton;
    DatabaseManager db;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);



            //TODO get logged in User ==> currentMitglied. Von Login Activity get username und password
            //getCurrentMitglied();

            //Des isada nur zum Testen
            currentMitglied = new Mitglied(1, Dienstgrad.Brandinspektor,"Andreas", "Drabosenig",
                    new Stuetzpunkt(1,"Ledenitzen Feuerwehrhaus","Ledenitzen", 9581, "St.Martinerstraße", "3")
                    ,"andi","andi123");
            currentStuetzpunkt = currentMitglied.getStuetzpunkt();
            getEinsaetzeFromMitglied();

            radioButtonStuetzpunkt = findViewById(R.id.radioButtonStuetzpunktFilter);
            radioButtonEinsatz = findViewById(R.id.radioButtonEinsatzFilter);
            radioGroupFilter = findViewById(R.id.radioGroupFilter);
            resetButton = findViewById(R.id.buttonReset);

            resetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    googleMap.clear();
                    radioButtonStuetzpunkt.setChecked(false);
                    radioButtonEinsatz.setChecked(false);
                    showEinsaetze();
                    showStuetzpunkt();
                }
            });

            radioGroupFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    googleMap.clear();
                    if(checkedId == radioButtonEinsatz.getId()){
                        showEinsaetze();
                    }else if(checkedId == radioButtonStuetzpunkt.getId()){
                        showStuetzpunkt();
                    }
                }
            });

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);

            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCurrentMitglied() throws Exception {
        ArrayList<Mitglied> mitgliedList = db.getAllMitglieder();
        //TODO mit dem zeugs was i von login bekomme des current Mitglied holen
        for(Mitglied m : mitgliedList){
            if(m.getUsername().equals(username) && m.getPassword().equals(password))
            {
                currentMitglied = m;
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        gc = new Geocoder(this);
        googleMap.setOnMarkerClickListener(this);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(46.7525, 13.8617)).zoom(7.5f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.moveCamera(cameraUpdate);

        //TODO einsatzList = getEinsaetzeFromDatabase(); Aber nur Einsätze von currentMitglied

        showStuetzpunkt();
        showEinsaetze();
    }

    private void showEinsaetze() {
        //TODO Change from se Stützpunkt to se Einsatz
        /*
        try {
            for (Stuetzpunkt stuetzpunkt : stuetzpunktList){
                if(currentMitglied.getStuetzpunkt().getId() == stuetzpunkt.getId()){
                    List<Address> list = gc.getFromLocationName(stuetzpunkt.getAddress(), 1);
                    Address add = list.get(0);
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(add.getLatitude(), add.getLongitude()))
                            .icon(bitmapDescriptorFromVector(this, R.drawable.ic_home_black_24dp))
                            .title(stuetzpunkt.getName())
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(46.7525, 14.88))
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_fire))
                .title("Feuer")
                .snippet(new Einsatz().getClass().getName())
        );

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(46.9525, 14.88))
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_sports_car))
                .title("Unfall")
                .snippet(new Einsatz().getClass().getName())
        );

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(46.3525, 14.7))
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_tools_cross_settings_symbol_for_interface))
                .title("Technischer Einsatz")
                .snippet(new Einsatz().getClass().getName())
        );

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(46.4525, 14.88))
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_customer_service))
                .title("Hilfeleistung")
                .snippet(new Einsatz().getClass().getName())
        );

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(46.3525, 14.78))
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_man_user))
                .title("Personensuche")
                .snippet(new Einsatz().getClass().getName())
        );
    }

    private void showStuetzpunkt() {
        try {
            List<Address> list = gc.getFromLocationName(currentStuetzpunkt.getAddress(), 1);
            Address add = list.get(0);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(add.getLatitude(), add.getLongitude()))
                    .icon(bitmapDescriptorFromVector(this, R.drawable.ic_home_black_24dp))
                    .title(currentStuetzpunkt.getName())
                    .snippet(currentStuetzpunkt.getClass().getName())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getEinsaetzeFromMitglied() throws Exception {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.getSnippet().equals(new Stuetzpunkt().getClass().getName())){
            showPopupStuetzpunkt(this);
        }else if(marker.getSnippet().equals(new Einsatz().getClass().getName())){
            showPopupEinsatz(this);
        }

        return true;
    }

    private void showPopupStuetzpunkt(final Activity context) {
        int popupWidth = 550;
        int popupHeight = 500;

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
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY,200,700);

        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.close);
        TextView txtName = (TextView) layout.findViewById(R.id.txtName);
        TextView txtOrt = (TextView) layout.findViewById(R.id.txtOrt);
        TextView txtPLZ = (TextView) layout.findViewById(R.id.txtPLZ);
        TextView txtStrasse = (TextView) layout.findViewById(R.id.txtStrasse);

        txtName.setText("Name: " + currentStuetzpunkt.getName());
        txtOrt.setText("Ort: " + currentStuetzpunkt.getOrt());
        txtPLZ.setText("PLZ: " + currentStuetzpunkt.getPlz());
        txtStrasse.setText("Straße: " + currentStuetzpunkt.getStrasse() + " " + currentStuetzpunkt.getHausnr());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
    }

    private void showPopupEinsatz(final Activity context) {
        int popupWidth = 200;
        int popupHeight = 150;

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
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY,12,12);

        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_placeholder);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(23, 17, vectorDrawable.getIntrinsicWidth() + 23, vectorDrawable.getIntrinsicHeight() + 17);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}