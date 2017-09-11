package com.tempus.timewalk.timewalk.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tempus.timewalk.timewalk.Activity.MapActivity;
import com.tempus.timewalk.timewalk.CardView.CardAdapter2;
import com.tempus.timewalk.timewalk.Classes.DirectionListener;
import com.tempus.timewalk.timewalk.Classes.Directions;
import com.tempus.timewalk.timewalk.Models.Points;
import com.tempus.timewalk.timewalk.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * A {@link Fragment} subclass that display the map screen.
 */
public class MapsFragment extends Fragment implements LocationListener,
        OnMapReadyCallback,
        DirectionListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.InfoWindowAdapter,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    // TODO: Rename parameter arguments, choose names that match

    /**
     * Variables
     * The fragment initialization parameters, e.g. ARG_ITEM_NUMBER
     */
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GoogleMap mMap;
    private MapView mMapView;
    private LatLng latLng;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Marker> wayPointsMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private static final String TAG = MapActivity.class.getSimpleName();
    private LocationManager locationManager;
    private RecyclerView recyclerView;
    private CardAdapter2 cardAdapter2;
    private String places;

    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MapsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapsFragment newInstance(String param1, String param2) {
        MapsFragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Get string places upon instantiation from the RecommendedRoutesFragment
     *
     * @param savedInstanceState a Bundle object containing the activity's previously saved state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        places = getActivity().getIntent().getStringExtra("places");
    }

    /**
     * Create view upon initialisation.
     *
     * @param view The parents view to set on
     * @param savedInstanceState a Bundle object containing the activity's previously saved state.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) view.findViewById(R.id.map);

        if(mMapView!= null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
        setListPlaces(view,places);
    }


    /**
     * Create and inject data into the display cards below the map.
     *
     * @param view The parents view to set on.
     * @param places a Bundle object containing the activity's previously saved state.
     */
    private void setListPlaces(View view, String places) {
        String[] name = new String[]{""};
        switch (places){
            case "Family Walk" :
                name = new String[]{"City Hall", "Anzac Square", "Roma Street Park"};
                break;

            case "Retro Tour":
                name = new String[]{"City Hall", "St.Stephen Cathedral", "Story Bridge"};
                break;
            case "Sports Tour":
                name = new String[]{"a","b","c"};
                break;
        }
        cardAdapter2 = new CardAdapter2(getContext(), name);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cardAdapter2);

    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the
     *                 fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be
     *                  attached to.
     * @param savedInstanceState a Bundle object containing the activity's previously saved state.
     * @return A new instance of fragment HomeFragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_maps, container, false);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    */

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * After calling connect(), this method will be invoked asynchronously when the connect request
     * has successfully completed. After this callback, the application can make requests on other
     * methods provided by the client and expect that no user intervention is required to call
     * methods that use account and scopes provided to the client constructor.
     *
     * @param bundle Bundle of data provided to clients by Google Play services.
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected!");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    /**
     * Called when the client is temporarily in a disconnected state.
     *
     * @param i The reason for the disconnection.
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * Called when the location changed, find the current location and send the request.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(18).build();
        mMap.moveCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        sendRequestAPI(lat,lng, places);

        LatLng sydney = new LatLng(lat, lng);
        //mMap.addMarker(new MarkerOptions().position(sydney));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    /**
     * Called when the map is ready to be used.
     *
     * @param googleMap A non-null instance of a GoogleMap associated with the MapFragment or
     *                  MapView that defines the callback.
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mMap = googleMap;
        mMap.setMinZoomPreference(13.0f);
        mMap.setMaxZoomPreference(20.0f);
        mMap.setOnMapClickListener(this);
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(MyOnInfoWindowClickListener);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.
                        ACCESS_FINE_LOCATION}, 1);
            } else {
                mMap.setMyLocationEnabled(true);
            }
        }

        mMap.setMyLocationEnabled(true);

        buildGoogleApiClient();
        mGoogleApiClient.connect();

    }

    /**
     * Builder to configure a GoogleApiClient.
     */
    private void buildGoogleApiClient() {
        Log.d(TAG, "buildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    
    private void sendRequestAPI(Double lat,Double lng, String places) {

        String origin = String.valueOf(lat) + "," + String.valueOf(lng);
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        String destination = "0,0";
        String wayPoints = "0,0";

        switch (places) {
            case "Family Walk" :
                destination = "-27.463806,153.020112";
                wayPoints = "-27.465992,153.027562 | -27.468974,153.023442";
                break;

            case "Retro Tour" :
                destination = "-27.464028, 153.035722";
                wayPoints = "-27.468952, 153.023554|-27.468602, 153.029011";
                break;

            case "Sports Tour" :
                destination = "";
                wayPoints = "";
                break;

        }
        //String destination = "-27.494721,153.014262";
        //String wayPoints = "-27.498172, 153.013585";
        try {

            new Directions(this, origin, destination, wayPoints).execute();
            Double latDes= Double.parseDouble(destination.split(",")[0]);
            Double lngDes= Double.parseDouble(destination.split(",")[1]);
            String [] points = wayPoints.split(Pattern.quote("|")) ;

            for(String point : points) {
                Double latPoint = Double.parseDouble((point.split(",")[0]));
                Double lngPoint = Double.parseDouble((point.split(",")[1]));
                List<Address> addressesPoint = geocoder.getFromLocation(latPoint, lngPoint, 1);
                wayPointsMarkers.add(mMap.addMarker(new MarkerOptions().title(addressesPoint.get(0).getAddressLine(0))
                        .position(new LatLng(latPoint, lngPoint))));
            }

            List<Address> addressesStart = geocoder.getFromLocation(lat, lng, 1);
            List<Address> addressesEnd = geocoder.getFromLocation(latDes,
                    lngDes, 1);
            originMarkers.add(mMap.addMarker(new MarkerOptions().title(addressesStart.get(0).getAddressLine(0))
                    .position(new LatLng(lat, lng))));
            destinationMarkers.add(mMap.addMarker((new MarkerOptions().title(addressesEnd.get(0).getAddressLine(0))
            .position(new LatLng(latDes, lngDes)))));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latDes,
                    lngDes), 16));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    GoogleMap.OnInfoWindowClickListener MyOnInfoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {

        }
    };

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

        if (wayPointsMarkers != null) {
            for (Marker marker:wayPointsMarkers ) {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(getContext(), "This app needs location permission to be granted!,",
                            Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
                break;
        }
    }

    @Override
    public void onDirectionSuccess(List<Points> point) {
        for (Points points:point){
            PolylineOptions polyLineOptions = new PolylineOptions().geodesic(true).color(Color.BLUE
            ).width(15);
            for (int i = 0; i < points.points.size(); i++)
                polyLineOptions.add(points.points.get(i));

            polylinePaths.add(mMap.addPolyline(polyLineOptions));

        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
