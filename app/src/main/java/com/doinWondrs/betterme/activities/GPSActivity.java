package com.doinWondrs.betterme.activities;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
import android.Manifest;
//import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
//import android.view.View;
import android.widget.ImageButton;
import com.doinWondrs.betterme.R;
import com.doinWondrs.betterme.helpers.FetchData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.doinWondrs.betterme.databinding.ActivityTestGoogleMapBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class GPSActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    public double lng,lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        fusedLocationClient.flushLocations();

        setContentView(R.layout.activity_gpsactivity);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        navGoTo();
        findNearestGym();
        findNearestPark();
        findNearestSupplements();

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

        setUpLocation();
    }


    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if(location == null){
                Log.e("TESTING MAP", "Location was null");
            }
            lat = location.getLatitude();
            lng = location.getLongitude();

            LatLng latLng = new LatLng(lat,lng);
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon))
                    .title("Current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

            Log.i("TESTING MAP", "Our Lat: " + lat);
            Log.i("TESTING MAP", "Our Lon: " + lng);
        });
    }

    private void findNearestGym()
    {
        if (mMap != null) mMap.clear();

        ImageButton gymBtn = findViewById(R.id.gymImageBtn);
        gymBtn.setOnClickListener(v -> {

                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location=").append(lat).append(",").append(lng);
                stringBuilder.append("&radius=8046");// in meters // 5 mile search radius
                stringBuilder.append("&types=gym");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=").append(getResources().getString(R.string.google_map_key));


                String url = stringBuilder.toString();

                Object dataFetch[] = new Object[2];
                dataFetch[0] = mMap;
                dataFetch[1] = url;

                FetchData fetchData = new FetchData();

                fetchData.execute(dataFetch);

        });
    }

    private void findNearestPark()
    {
        ImageButton trailBtn = findViewById(R.id.trail);
        trailBtn.setOnClickListener(v -> {

            StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            stringBuilder.append("location=").append(lat).append(",").append(lng);
            stringBuilder.append("&radius=8046");// in meters // 5 mile search radius
            stringBuilder.append("&types=park");
            stringBuilder.append("&sensor=true");
            stringBuilder.append("&key=").append(getResources().getString(R.string.google_map_key));


            String url = stringBuilder.toString();

            Object dataFetch[] = new Object[2];
            dataFetch[0] = mMap;
            dataFetch[1] = url;

            FetchData fetchData = new FetchData();

            fetchData.execute(dataFetch);

        });
    }

    private void findNearestSupplements()
    {
        ImageButton suppBtn = findViewById(R.id.drugStore);
        suppBtn.setOnClickListener(v -> {

            StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            stringBuilder.append("location=").append(lat).append(",").append(lng);
            stringBuilder.append("&radius=8046");// in meters // 5 mile search radius
            stringBuilder.append("&types=drugstore");
            stringBuilder.append("&sensor=true");
            stringBuilder.append("&key=").append(getResources().getString(R.string.google_map_key));


            String url = stringBuilder.toString();

            Object dataFetch[] = new Object[2];
            dataFetch[0] = mMap;
            dataFetch[1] = url;

            FetchData fetchData = new FetchData();

            fetchData.execute(dataFetch);

        });
    }

    public void navGoTo()
    {
        //NOTES: https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/
        //NOTES: bottomnavbar is deprecated: https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView.OnNavigationItemSelectedListener

        //initialize, instantiate
        NavigationBarView navigationBarView;//new way to do nav's but more research needed
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //set home selected: home
        bottomNavigationView.setSelectedItemId(R.id.gps_nav);
        //perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home_nav:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.calendar_nav:
                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.gps_nav:
                        //we are here right now
                        break;
                    case R.id.workouts_nav:
                        startActivity(new Intent(getApplicationContext(), WorkoutPageFirst.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.settings_nav:
                        startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    default: return false;// this is to cover all other cases if not working properly
                }

                return true;
            }
        });//end lambda: bottomNavview
    }//end method: navGoTo
}