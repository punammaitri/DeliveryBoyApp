package com.aiprous.deliveryboy.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.aiprous.deliveryboy.MainActivity;
import com.aiprous.deliveryboy.R;
import com.aiprous.deliveryboy.activity.OrderDetails;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {


    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private LatLng mCurrentLatLng;
    private String mCurrentAddress;
    ArrayList<LatLng> MarkerPoints;

    @BindView(R.id.chart)
    BarChart mChart;

    private MainActivity mainActivity;

    private OnFragmentInteractionListener mListener;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public DashboardFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

        MarkerPoints = new ArrayList<>();

        BarData data = new BarData(getXAxisValues(), getDataSet());
        mChart.setData(data);

        mChart.setDescription("");
        mChart.animateXY(2000, 2000);
        mChart.getXAxis().setEnabled(true);
        mChart.setDrawValueAboveBar(false);
        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        //mChart.setDrawValueAboveBar(true);

        //for X axis
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);

        XAxis xLabels = mChart.getXAxis();
        xLabels.setTextColor(R.color.colorlightlightlightGray);


        //for Y axis
        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setDrawGridLines(false);
        yAxis.setDrawAxisLine(false);

        mChart.setDrawGridBackground(false);

        mChart.getLegend().setEnabled(false);
        mChart.invalidate();

    }


    public class SubListModel {
        int image;
        String product_name;

        public SubListModel(int image, String product_name) {
            this.image = image;
            this.product_name = product_name;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void onFragmentTrans(Fragment framgent) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, framgent).commit();
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(90.0f, 0);
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(50.0f, 1);
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(80.0f, 2);
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(70.0f, 3);
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(50.0f, 4);
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(85.0f, 5);
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(30.0f, 6);
        valueSet1.add(v1e7);

        BarDataSet barDataSet2 = new BarDataSet(valueSet1, "");
        barDataSet2.setColor(Color.rgb(31, 44, 76));
        barDataSet2.setDrawValues(false);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet2);

        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("MON");
        xAxis.add("TUE");
        xAxis.add("WED");
        xAxis.add("THU");
        xAxis.add("FRI");
        xAxis.add("SAT");
        xAxis.add("SUN");
        return xAxis;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
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
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, DashboardFragment.this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //get current address from latlng
        mCurrentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        mCurrentAddress = getAddressFromLatLng(getActivity(), location.getLatitude(), location.getLongitude());

        //Mark pickup and drop on map
        markPickUpandDropOnMap(mCurrentLatLng, mCurrentAddress);

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, DashboardFragment.this);
        }
    }

    private String getAddressFromLatLng(Context context, double latitude, double longitude) {
        String strAdd = null;
        try {
            Geocoder geocoder;
            List<Address> addresses;
            strAdd = "";
            geocoder = new Geocoder(context, Locale.getDefault());

            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                strAdd = address + " " + city;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strAdd;
    }

    public void markPickUpandDropOnMap(LatLng point, String address) {
        // Already two locations
        MarkerPoints.clear();
        mMap.clear();

        // Adding new item to the ArrayList
        MarkerPoints.add(point);

        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();

        // Setting the position of the marker
        options.position(point);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


        DrawRouteMaps.getInstance(getActivity())
                .draw(point, mCurrentLatLng, mMap);


        if (MarkerPoints.size() == 1) {
            DrawMarker.getInstance(getActivity()).draw(mMap, point, R.drawable.marker_a, "" + address);
            LatLngBounds bounds = new LatLngBounds.Builder()
                    .include(point)
                    .include(mCurrentLatLng).build();
            Point displaySize = new Point();
            getActivity().getWindowManager().getDefaultDisplay().getSize(displaySize);
            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

            //options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(address);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
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
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }
}