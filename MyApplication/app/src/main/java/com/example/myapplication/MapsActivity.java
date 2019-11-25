package com.example.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.util.MapUtils;
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
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(46.7525, 14))
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_home_black_24dp))
                .title("Stützpunkt")
        );

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


        /*
        Drawable circleDrawable = getResources().getDrawable(R.drawable.einsaetze_circle);
        Bi

        mMap.addMarker(new MarkerOptions()
                .snippet("E")
                .position()
                .title("Einsatz")
                .icon(markerIcon));


        circleDrawable = getResources().getDrawable(R.drawable.stuetzpunkte_circle);
        markerIcon = getMarkerIconFromDrawable(circleDrawable);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(46.7525, 14))
                .title("Stuetzpunkt")
                .icon(markerIcon));
*/
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(46.7525, 13.8617)).zoom(7.5f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.moveCamera(cameraUpdate);
    }
}