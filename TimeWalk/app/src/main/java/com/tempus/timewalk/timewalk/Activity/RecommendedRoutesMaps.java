package com.tempus.timewalk.timewalk.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tempus.timewalk.timewalk.Classes.Directions;
import com.tempus.timewalk.timewalk.Classes.DirectionListener;
import com.tempus.timewalk.timewalk.Models.Points;
import com.tempus.timewalk.timewalk.Models.Routes;
import com.tempus.timewalk.timewalk.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RecommendedRoutesMaps extends FragmentActivity implements
        LocationListener,
        OnMapReadyCallback,
        DirectionListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.InfoWindowAdapter,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private LatLng latLng;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private static final String TAG = RecommendedRoutesMaps.class.getSimpleName();

    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_routes_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        sendRequestAPI();
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(13.0f);
        mMap.setMaxZoomPreference(20.0f);
        mMap.setOnMapClickListener(this);
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(MyOnInfoWindowClickListener);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            Log.e(TAG, "Location Unavailable");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            mMap.setMyLocationEnabled(true);
            Toast.makeText(getApplicationContext(), "Location Unavailable",
                    Toast.LENGTH_LONG).show();
        }

        buildGoogleApiClient();
        mGoogleApiClient.connect();

        // Add a marker in Sydney and move the camera

        LatLng sydney = new LatLng(-27.499374,153.015067);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void sendRequestAPI() {

        //String latitude = Double.toString(lastLocation.getLatitude());
        //String longitude = Double.toString(lastLocation.getLongitude());
        //String origin = latitude + ","+ longitude;
        /*
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
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double org1 = location.getLatitude();
        double org2 = location.getLongitude();
        String origin = String.valueOf(org1) + "," + String.valueOf(org2); */
        String origin = "-27.499374,153.015067";
        String destination = "-27.494721,153.014262";
        String wayPoints = "-27.498172, 153.013585";
        try {
            new Directions(this, origin, destination, wayPoints).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    protected synchronized void buildGoogleApiClient() {
        Log.d(TAG, "buildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    GoogleMap.OnInfoWindowClickListener MyOnInfoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {

        }
    };



    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(18).build();
        mMap.moveCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected!");
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onDirectionStart() {
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }

    }

    @Override
    public void onDirectionSuccess(List<Routes> routes, List<Points> point) {
        for (Routes route: routes ){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            originMarkers.add(mMap.addMarker(new MarkerOptions().title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions().title(route.endAddress)
                    .position(route.endLocation)));
        }
        for (Points points:point){
            PolylineOptions polyLineOptions = new PolylineOptions().geodesic(true).color(Color.BLUE
            ).width(15);

            for (int i = 0; i < points.points.size(); i++)
                polyLineOptions.add(points.points.get(i));

            polylinePaths.add(mMap.addPolyline(polyLineOptions));

        }

    }
}
