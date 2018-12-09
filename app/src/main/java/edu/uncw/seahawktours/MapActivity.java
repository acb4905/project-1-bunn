package edu.uncw.seahawktours;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.android.gms.tasks.Task;


import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    //AIzaSyDS3-K5aDxTa6njUOhSaf8ZNJPk3y_ijaM
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private GeoDataClient mGeoDataClient;
    private GeofencingClient mGeofencingClient;
    private boolean mLocationPermissionGranted=true;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 99;
    private static final int MINIMUM_DWELL_DURATION_IN_SECS = 5;
    public static int DEFAULT_ZOOM=17;
    public static final String TAG = "UNCW";
    private Location mLastKnownLocation;
    private LatLng mDefaultLocation= new LatLng (34.226792, -77.872217);
    private int toggle = 1;
    private ArrayList<String> listItems=new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView infoView;
    private Box<Building> buildingBox;
    private TextView title;

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

    PendingIntent mGeofencePendingIntent;



    //get position and use map
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        buildingBox = ((App) getApplication()).getBoxStore().boxFor(Building.class);
        infoView=findViewById(R.id.buildingList);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        infoView.setAdapter(adapter);
        title=findViewById(R.id.title);

        //Create the list listener
        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listDrinks,
                                    View itemView,
                                    int position,
                                    long id) {
                //pass the drink the user clicks on to the DrinkActivity
                Intent intent = new Intent(MapActivity.this, DetailActivity.class);
                List<Building> geofenceBuilding = buildingBox.query().equal(Building_.name, (String)listDrinks.getItemAtPosition((int)id)).build().find();
                if(!geofenceBuilding.isEmpty()){
                    intent.putExtra(DetailActivity.EXTRA_BUILDINGID,geofenceBuilding.get(0).getId());
                    startActivity(intent);
                }
            }
        };
        //Assign the listener to the list view
        infoView.setOnItemClickListener(itemClickListener);

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
                mLastKnownLocation = null;
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

    private static final String GEOFENCE_NOTIFICATION_ACTION = "edu.uncw.seahawktours.MY_GEOFENCE_NOTIFICATION";


    private PendingIntent getGeofencePendingIntent() {
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent();
        intent.setAction(GEOFENCE_NOTIFICATION_ACTION);
        mGeofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }

    private void addGeofences() {
        final Context context = this;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            mGeofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            String msg = "Geofences successfully added";
                            //listItems.add("Building Name of Geofence");
                            Toast.makeText( context, msg, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, msg);
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, e.getMessage());
                            e.printStackTrace();
                            String msg = "Failed to add Geofences, please change your location settings to High Accuracy";
                            Toast.makeText( context, msg, Toast.LENGTH_LONG).show();
                            Log.e(TAG, msg);
                        }
                    });
        }
    }

    private GeofencingRequest getGeofencingRequest() {
        // Creates a list of geofencing objects from the database
        List<Geofence> geofenceList = new ArrayList<>();
        for (Building building : buildingBox.getAll()) {
            Geofence g = new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this
                    // geofence.
                    .setRequestId(building.getName())
                    .setCircularRegion(
                            building.getLat(),
                            building.getLon(),
                            building.getRadius()
                    )
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .setLoiteringDelay(MINIMUM_DWELL_DURATION_IN_SECS)
                    .build();
            geofenceList.add(g);
        }

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    private double getDistanceFromPoint(double userLat, double userLon, double pointLat, double pointLon){
        double x = (userLat-pointLat)*(userLat-pointLat);
        double y = (userLon - pointLon)*(userLon-pointLon);
        double distanceInLatLon= Math.sqrt(x+y);
        return (((distanceInLatLon*55.2428)*1000000000)/1000000000);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getApplicationContext(), "Geofence update received!", Toast.LENGTH_SHORT).show();
            GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
            Log.d(TAG, geofencingEvent.toString());

            if (geofencingEvent.hasError()) {
                Log.e(TAG, GeofenceStatusCodes.getStatusCodeString(
                        geofencingEvent.getErrorCode()));
                return;
            }

            // Get the transition type.
            int geofenceTransition = geofencingEvent.getGeofenceTransition();

            // Test that the reported transition was of interest.
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL ||
                    geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

                // Get the geofences that were triggered. A single event can trigger
                // multiple geofences.
                List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
                if (mLastKnownLocation!=null) {
                    int runs = 0;
                    listItems.clear();
                    for (Geofence geofence : triggeringGeofences) {
                        if (runs == 0) {
                            title.setText("Nearby Buildings");
                            title.setTextSize(25);
                        }
                        List<Building> geofenceBuilding = buildingBox.query().equal(Building_.name, geofence.getRequestId()).build().find();
                        listItems.add(geofence.getRequestId() + "    " + getDistanceFromPoint(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), geofenceBuilding.get(0).getLat(), geofenceBuilding.get(0).getLon()) + " mi");
                        adapter.notifyDataSetChanged();
                        runs += 1;
                    }
                    Toast.makeText(getApplicationContext(), "Nearby Buildings Added", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                title.setText("Too far from UNCW :(");
                listItems.add("You are currently not within .25 miles of a UNCW building");
                listItems.add("Tap the map button again to return your map to campus");
                adapter.notifyDataSetChanged();
            }
        }
    };


    public void onClickDone(View view) {
        toggle=toggle+1;
        if (toggle%2==0) {
            getDeviceLocation();
            getGeofencePendingIntent();
            title.setText("Too far from UNCW :(");
            title.setTextSize(25);
            listItems.add("You are currently not within .25 miles of a UNCW building");
            listItems.add("Tap the map button again to return your map to campus");
            adapter.notifyDataSetChanged();
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
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        // We should stop listening for Geofence updates if we're not in the app
        unregisterReceiver(receiver);
        mGeofencingClient.removeGeofences(getGeofencePendingIntent())
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String msg = "Geofences successfully removed!";
                        Log.d(TAG, msg);
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Failed to remove Geofences!");
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(GEOFENCE_NOTIFICATION_ACTION));
        addGeofences();
    }



}
