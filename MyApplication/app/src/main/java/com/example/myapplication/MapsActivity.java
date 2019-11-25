package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        List<Stuetzpunkt> stuetzpunktList = new ArrayList<>();
        // = getStuetzpunkte();
        stuetzpunktList.add(new Stuetzpunkt(1,"Ledenitzen Feuerwehrhaus","Ledenitzen", 9581, "St.Martinerstraße", "3"));
        stuetzpunktList.add(new Stuetzpunkt(2,"Latschach Feuerwehrhaus","Latschach", 9582, "Kulturhausstraße ", "13"));

        List<Einsatz> einsatzList = getEinsaetze();

        Geocoder gc = new Geocoder(this);
        try {
            for (Stuetzpunkt stuetzpunkt : stuetzpunktList){
                List<Address> list = gc.getFromLocationName(stuetzpunkt.getAddress(), 1);
                Address add = list.get(0);
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(add.getLatitude(), add.getLongitude()))
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_home_black_24dp))
                        .title(stuetzpunkt.getName())
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }





        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(46.7525, 14.88))
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_fire))
                .title("Feuer")
        );

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(46.9525, 14.88))
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_sports_car))
                .title("Unfall")
        );

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(46.3525, 14.7))
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_tools_cross_settings_symbol_for_interface))
                .title("Technischer Einsatz")
        );

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(46.4525, 14.88))
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_customer_service))
                .title("Hilfeleistung")
        );

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(46.3525, 14.78))
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_man_user))
                .title("Personensuche")
        );

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(46.7525, 13.8617)).zoom(7.5f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.moveCamera(cameraUpdate);
    }

    private List<Stuetzpunkt> getStuetzpunkte() {
        return null;
    }

    private List<Einsatz> getEinsaetze() {

        return null;
    }
}