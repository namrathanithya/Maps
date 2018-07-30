package com.example.namra.mvvm_map_new.view;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.namra.mvvm_map_new.R;
import com.example.namra.mvvm_map_new.adapter.ListMapAdapter;
import com.example.namra.mvvm_map_new.model.Example;
import com.example.namra.mvvm_map_new.model.Result;
import com.example.namra.mvvm_map_new.viewmodel.GeoViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


//import static com.example.namra.mvvm_map_new.retro.RetroClass.build_retrofit_and_get_response;
//import static com.example.namra.mvvm_map_new.retro.RetroClass.getRetrofitMaps;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    @BindView(R.id.mainrecycler)
    RecyclerView recyclerView;
    private GeoViewModel geoViewModel;
    Observer<Example> example;
    String searchInput = new String();
    Double mLongitude;
    Double mLatitude;
    String str = new String();
    private int PROXIMITY_RADIUS = 10000;
    GoogleApiClient mGoogleApiClient;
    Location location;
    private GoogleMap googleMap;
    String string;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    Context context;
    // RecyclerViewModel recyclerViewModel;
    ListMapAdapter listadapter;
    View view;
    List<Result> maps;
    LatLng latLng;
    MarkerOptions markerOptions;
    FloatingActionButton floatingActionButton;
    Bundle bundle;
    ClusterManager<Example> mClusterManager;
   Example examples;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_settings);


        listadapter = new ListMapAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listadapter);

        geoViewModel = ViewModelProviders.of(this).get(GeoViewModel.class);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_settings);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(MapsActivity.this, "active", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MapsActivity.this,RecyclerActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putParcelable("Data",examples);
                intent.putExtra("bundle1",bundle1);
                startActivity(intent);
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        if (!isGooglePlayServicesAvailable()) {
            Toast.makeText(this, "Google Play Services not available.", Toast.LENGTH_LONG).show();
            this.finish();
        } else {
            // when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        // inflater.inflate(R.menu.menu, menu);
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.Search);
        final SearchView sv = new SearchView(MapsActivity.this.getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String string) {
                System.out.println("search query submit");
                searchInput = sv.getQuery().toString();
                findPlaces(searchInput, str, PROXIMITY_RADIUS);
                Log.e("####", searchInput);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                return false;
            }
        });
        return true;
    }

    private void findPlaces(String string, String str, int PROXIMITY_RADIUS) {
        geoViewModel.getMapLiveData(string, str, PROXIMITY_RADIUS);


        geoViewModel.getDataVal().observe(this, new Observer<Example>() {
            @Override
            public void onChanged(@Nullable Example example) {
                Log.e("Example", example.toString());

                if (example != null) {
                    setMapData(example);
                }
            }
        });
        geoViewModel.getDataVal().observe(this, new Observer<Example>() {
            @Override
            public void onChanged(@Nullable Example example) {
                listadapter.setMapList(example);
                listadapter.notifyDataSetChanged();
            }
        });

        geoViewModel.getDataVal().observe(this, new Observer<Example>() {
            @Override
            public void onChanged(@Nullable Example example) {
                examples=example;
            }
        });





    }

Double lat,lng;

    public void setMapData(Example example) {

        try {
            mClusterManager = new ClusterManager<Example>(this, googleMap);
            markerOptions = new MarkerOptions();
            mClusterManager.clearItems();
            for (int i = 0; i < example.getResults().size(); i++) {
                lat = example.getResults().get(i).getGeometry().getLocation().getLat();
                lng = example.getResults().get(i).getGeometry().getLocation().getLng();
                String placeName = example.getResults().get(i).getName();
                String vicinity = example.getResults().get(i).getVicinity();
                String image = example.getResults().get(i).getIcon();
                latLng = new LatLng(lat, lng);
                markerOptions.position(latLng);
                markerOptions.title(placeName + " : " + vicinity);
                addItems(lat, lng);
                Log.e("Lat: ", lat.toString());
                Log.e("Long: ", lng.toString());
                googleMap.clear();
                mClusterManager.cluster();

                mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Example>() {
                    @Override
                    public boolean onClusterClick(Cluster<Example> cluster) {
                        LatLngBounds.Builder builder = LatLngBounds.builder();
                        for (ClusterItem item:cluster.getItems())
                        {
                            builder.include(item.getPosition());
                        }
                        final LatLngBounds latLngBounds = builder.build();
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds,150));
                        return false;
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
   private void addItems(Double lat, Double lng){
        Example offsetItem = new Example(lat, lng);
        mClusterManager.addItem(offsetItem);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 10));
        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);
    }


    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                googleMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            googleMap.setMyLocationEnabled(true);
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            this.location = location;
            mLongitude = location.getLongitude();
            mLatitude = location.getLatitude();
            str = String.valueOf(mLatitude) + "," + String.valueOf(mLongitude);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(8));
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Could not connect google api", Toast.LENGTH_LONG).show();

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


}





