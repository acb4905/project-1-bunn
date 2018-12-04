package edu.uncw.seahawktours;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.ListView;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.GeofencingClient;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    //AIzaSyDS3-K5aDxTa6njUOhSaf8ZNJPk3y_ijaM
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private GeoDataClient mGeoDataClient;
    private GeofencingClient mGeofencingClient;
    private boolean mLocationPermissionGranted=true;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 99;
    public static int DEFAULT_ZOOM=17;
    public static final String TAG = "UNCW";
    private Location mLastKnownLocation;
    private LatLng mDefaultLocation= new LatLng (34.226792, -77.872217);
    private int toggle = 1;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    //Markers

    private static final LatLng CIS = new LatLng(34.226115,-77.871845);
    private static final LatLng RANDALL = new LatLng(34.227527,-77.873863);
    private static final LatLng DELOACH = new LatLng(34.228770,-77.874409);
    private static final LatLng BEAR = new LatLng(34.228482,-77.872816);
    private static final LatLng WAG = new LatLng(34.223247,-77.864902);

    private Marker cis;
    private Marker randall;
    private Marker deloach;
    private Marker bear;
    private Marker wag;

    //get position and use map
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGeofencingClient = LocationServices.getGeofencingClient(this);

        // Construct a GeoDataClient.
       mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        ListView listView= findViewById(R.id.buildingList);
        listView.setAdapter(adapter);

        //Geofences

        mGeofenceList.add(new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId(entry.getKey())

                .setCircularRegion(
                        entry.getValue().latitude,
                        entry.getValue().longitude,
                        Constants.GEOFENCE_RADIUS_IN_METERS
                )
                .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                //mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        updateLocationUI();
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();

                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                        } else {
                            Toast toast = Toast.makeText(MapActivity.this,"Your location is off! Please go to your settings to allow location in this app.", Toast.LENGTH_LONG);
                            toast.show();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    public void onClickDone(View view) {
        toggle=toggle+1;
        if (toggle%2==0) {
            getDeviceLocation();
        }
        else{
            resetMap();
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions()
                .position(mDefaultLocation)
                .title("UNC Wilmington")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mDefaultLocation));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mDefaultLocation , DEFAULT_ZOOM);
        mMap.animateCamera(cameraUpdate);

        cis = mMap.addMarker(new MarkerOptions()
                .position(CIS)
                .title("Computer Information Systems Building")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        cis.setTag(0);

        randall = mMap.addMarker(new MarkerOptions()
                .position(RANDALL)
                .title("Randall Library")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        randall.setTag(0);

        deloach = mMap.addMarker(new MarkerOptions()
                .position(DELOACH)
                .title("Deloach Hall")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        deloach.setTag(0);

        bear = mMap.addMarker(new MarkerOptions()
                .position(BEAR)
                .title("Bear Hall")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        bear.setTag(0);

        wag = mMap.addMarker(new MarkerOptions()
                .position(WAG)
                .title("Wagoner Hall")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        wag.setTag(0);
    }

    public void resetMap(){
        mMap.addMarker(new MarkerOptions()
                .position(mDefaultLocation)
                .title("UNC Wilmington")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mDefaultLocation));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mDefaultLocation , DEFAULT_ZOOM);
        mMap.animateCamera(cameraUpdate);

        cis = mMap.addMarker(new MarkerOptions()
                .position(CIS)
                .title("Computer Information Systems Building")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        cis.setTag(0);

        randall = mMap.addMarker(new MarkerOptions()
                .position(RANDALL)
                .title("Randall Library")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        randall.setTag(0);

        deloach = mMap.addMarker(new MarkerOptions()
                .position(DELOACH)
                .title("Deloach Hall")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        deloach.setTag(0);

        bear = mMap.addMarker(new MarkerOptions()
                .position(BEAR)
                .title("Bear Hall")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        bear.setTag(0);

        wag = mMap.addMarker(new MarkerOptions()
                .position(WAG)
                .title("Wagoner Hall")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        wag.setTag(0);
    }

/*    public void addItems(View v) {
        //Add close buildings
        listItems.add("Clicked : "+clickCounter++);
        adapter.notifyDataSetChanged();
    }*/

    //Doesnt make the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;

    }

    //Creates intents for toolbar buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //action to take when the action create order button is tapped
            case R.id.map:
                Intent intent1 = new Intent (this, MapActivity.class);
                startActivity(intent1);
                return true;
            case R.id.menu:
                Intent intent = new Intent (this, AboutActivity.class);
                startActivity(intent);
                return true;
/*            case R.id.up:
                Intent intentUp = new Intent (DetailActivity.this, MainActivity.class);
                startActivity(intentUp);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
