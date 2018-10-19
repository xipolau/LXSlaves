package com.slavery.anabivirtual.servidao;

/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//CAMERA MOVE https://developers.google.com/maps/documentation/android-sdk/events
//POLYGON https://developers.google.com/maps/documentation/android-sdk/polygon-tutorial
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;

import android.widget.ImageView;

import android.widget.Toast;


/**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location.
 * Permission for {@link android.Manifest.permission#ACCESS_FINE_LOCATION} is requested at run
 * time. If the permission has not been granted, the Activity is finished with an error message.
 */


//https://stackoverflow.com/questions/13722869/how-to-handle-ontouch-event-for-map-in-google-map-api-v2
//https://stackoverflow.com/questions/14013002/google-maps-android-api-v2-detect-touch-on-map

public class MapsActivity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMarkerClickListener
{
    private GoogleMap mMap;

    //MARKERS
    private static final LatLng CASAINDIA = new LatLng(38.707911, -9.137939);
    private static final LatLng ALFANDEGA = new LatLng(38.708386, -9.135485);
    private static final LatLng PRACAESCRAVOS = new LatLng(38.709236, -9.134746);

    private Marker mCasaIndia;
    private Marker mAlfandega;
    private Marker mPracaEscravos;

    //LOCATION
    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.slavery.anabivirtual.servidao.R.layout.activity_maps);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(com.slavery.anabivirtual.servidao.R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
//LOCATION
        mMap = map;


        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        //MARKERS
        // Add some markers to the map, and add a data object to each marker.
        mCasaIndia = mMap.addMarker(new MarkerOptions()
                .position(CASAINDIA)
                .title("Casa da Guiné e da India")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mCasaIndia.setTag(0);

        mAlfandega = mMap.addMarker(new MarkerOptions()
                .position(ALFANDEGA)
                .title("Alfandega")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mAlfandega.setTag(0);

        mPracaEscravos = mMap.addMarker(new MarkerOptions()
                .position(PRACAESCRAVOS)
                .title("Praça dos Escravos")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        mPracaEscravos.setTag(0);

        // LatLngBounds bounds = new LatLngBounds(ALFANDEGA,PRACAESCRAVOS);
        // mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
        mMap.animateCamera( CameraUpdateFactory.newCameraPosition(new CameraPosition(ALFANDEGA, 17, 0 , 0)));
        // mMap.animateCamera(CameraUpdateFactory.zoomIn());

        //  mMap.animateCamera( CameraUpdateFactory.zoomIn() );
        // Intent myIntent = new Intent(this, MyLocationDemoActivity.class);
        //    myIntent.putExtra("map", mMap); //Optional parameters
        //this.startActivity(myIntent)

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);

//LIGHT CLICK
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                getWindow().setBackgroundDrawableResource(com.slavery.anabivirtual.servidao.R.drawable.lisboa_pre_terremoto);
                Context context = getApplicationContext();
                CharSequence text = "TAP - toque leve!";
                int duration = Toast.LENGTH_SHORT;

                Toast.makeText(context, text, duration).show();

                Toast toast = new Toast(context);
                ImageView view = new ImageView(context);
                view.setImageResource(com.slavery.anabivirtual.servidao.R.drawable.lisboa_pre_terremoto);
                view.setMaxHeight(200);
                //view.setScaleType(ImageView.ScaleType.FIT_XY);
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(view);
                toast.show();
            }
        });
//LONG Click
        mMap.setOnMapLongClickListener((new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {

                Log.d("MyFirstApplication:", "Intent Vai chamar Old Map");

                CharSequence text = "TAP - toque longo!";
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                Toast.makeText(context, text, duration).show();

                activateOldMap_Activity();
            }
        }));



/*THREAD-------
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MapView mv = new MapView(getApplicationContext());
                    mv.onCreate(null);
                    mv.onPause();
                    mv.onDestroy();
                }catch (Exception ignored){

                }
            }
        }).start();
*/

//Compass
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }



    //MARKERS------------------------------------
    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

//OPEN NEW ACTYIVITY
        activateStory_Activity();


        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


//OTHER ACTIVITIES
    void activateOldMap_Activity()
    {        Intent intent = new Intent(MapsActivity.this,
            OldLisbonActivity.class);
        startActivity(intent);
    }

    void activateStory_Activity()
    {        Intent intent = new Intent(MapsActivity.this,
            StoryActivity.class);
        startActivity(intent);
    }


}//eo class




