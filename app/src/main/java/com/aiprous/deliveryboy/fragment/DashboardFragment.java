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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.aiprous.deliveryboy.MainActivity;
import com.aiprous.deliveryboy.R;
import com.aiprous.deliveryboy.activity.OrderTrackingActivity;
import com.aiprous.deliveryboy.utils.APIConstant;
import com.aiprous.deliveryboy.utils.CustomProgressDialog;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.github.mikephil.charting.charts.BarChart;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aiprous.deliveryboy.utils.APIConstant.DELIVERYBOY_TRACKING;
import static com.aiprous.deliveryboy.utils.BaseActivity.isNetworkAvailable;

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

    @BindView(R.id.order_recycler_view)
    RecyclerView order_recycler_view;

    @BindView(R.id.chart)
    BarChart mChart;

    ArrayList<DashboardFragment.ListModel> mlistModelsArray = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private MainActivity mainActivity;

    private OnFragmentInteractionListener mListener;
    private String mDeliveryBoyLat  = "";
    private String mDeliveryBoyLong = "";
    private Double deliveryBoyLat;
    private LatLng mDeliveryBoyLatLng;
    private Double deliveryBoyLong;
    private String mDeliveryBoyAddress;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> location = new ArrayList<HashMap<String, String>>();
    private String mWarehouseLong  ="";
    private String mWarehouseLat = "";
    private Double warehouseLat;
    private Double warehouseLong;
    private LatLng mWarehouseLatLng;
    private String mWarehouseAddress;
    private String mShippingLat  = "";
    private String mShippingLong = "";
    private Double shippingLong;
    private Double shippingLat;
    private LatLng mShippingLatLng;
    private String mShippingAddress;
    private Double Latitude = 0.00;
    private Double Longitude = 0.00;

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
        //add static data into array list
        mlistModelsArray.add(new DashboardFragment.ListModel(R.drawable.pending, "Pending Orders (20)"));
        mlistModelsArray.add(new DashboardFragment.ListModel(R.drawable.processing, "Processing Orders (20)"));
        mlistModelsArray.add(new DashboardFragment.ListModel(R.drawable.checked, "Completed Orders (20)"));

        layoutManager = new LinearLayoutManager(getActivity());
        order_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        order_recycler_view.setHasFixedSize(true);
        order_recycler_view.setAdapter(new DashboardAdapter(getActivity(), mlistModelsArray));

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


    public class ListModel {
        int image;
        String title;

        public ListModel(int image, String title) {
            this.image = image;
            this.title = title;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

    /*    //get current address from latlng
        mCurrentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        mCurrentAddress = getAddressFromLatLng(getActivity(), location.getLatitude(), location.getLongitude());

        //Mark pickup and drop on map
        markPickUpandDropOnMap(mCurrentLatLng, mCurrentAddress);*/

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

   /* public void markPickUpandDropOnMap(LatLng point, String address) {
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
    }*/

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

    @Override
    public void onResume() {
        super.onResume();

        if (!isNetworkAvailable(getActivity())) {
            CustomProgressDialog.getInstance().showDialog(getActivity(), getActivity().getResources().getString(R.string.check_your_network), APIConstant.ERROR_TYPE);
        } else {
            CustomProgressDialog.getInstance().showDialog(getActivity(), "", APIConstant.PROGRESS_TYPE);
            TrackDeliveryBoyLocation("19", "5", "26.767633", "75.831374");
        }
    }

    private void TrackDeliveryBoyLocation(String orderId, String DboyId, String mLat, String mLong) {
        AndroidNetworking.get(DELIVERYBOY_TRACKING + orderId + "&dboy_id=" + DboyId + "&lat=" + mLat + "&long=" + mLong)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        // do anything with response
                        JsonObject getAllResponse = (JsonObject) new JsonParser().parse(response.toString());
                        JsonObject responseObject = getAllResponse.get("data").getAsJsonObject();
                        String status = getAllResponse.get("status").getAsString();

                        if (status.equals("success")) {

                            location.clear();
                            //for delivery boy latlong
                            JsonObject resDeliveryBoy = responseObject.get("delivery_boy").getAsJsonObject();
                            if (resDeliveryBoy != null) {
                                mDeliveryBoyLat = resDeliveryBoy.get("lat").getAsString();
                                mDeliveryBoyLong = resDeliveryBoy.get("long").getAsString();

                                //for warehouse latlong
                                if (!mDeliveryBoyLat.equals("") && !mDeliveryBoyLong.equals("")) {
                                    deliveryBoyLat = Double.valueOf(mDeliveryBoyLat);
                                    deliveryBoyLong = Double.valueOf(mDeliveryBoyLong);
                                    //for warehouse
                                    mDeliveryBoyLatLng = new LatLng(deliveryBoyLat, deliveryBoyLong);
                                    mDeliveryBoyAddress = getAddressFromLatLng(getActivity(), deliveryBoyLat, deliveryBoyLong);

                                    //add location to arrayList for warehouse
                                    map = new HashMap<String, String>();
                                    map.put("Latitude", mDeliveryBoyLat);
                                    map.put("Longitude", mDeliveryBoyLong);
                                    map.put("LocationName", mDeliveryBoyAddress);
                                    location.add(map);
                                }
                            }

                            //for warehouse boy latlong
                            JsonObject resWarehouse = responseObject.get("warehouse").getAsJsonObject();
                            if (resDeliveryBoy != null) {
                                mWarehouseLat = resWarehouse.get("lat").getAsString();
                                mWarehouseLong = resWarehouse.get("long").getAsString();

                                //for warehouse latlong
                                if (!mWarehouseLat.equals("") && !mWarehouseLong.equals("")) {
                                    warehouseLat = Double.valueOf(mWarehouseLat);
                                    warehouseLong = Double.valueOf(mWarehouseLong);
                                    //for warehouse
                                    mWarehouseLatLng = new LatLng(warehouseLat, warehouseLong);
                                    mWarehouseAddress = getAddressFromLatLng(getActivity(), warehouseLat, warehouseLong);

                                    //add location to arrayList for warehouse
                                    map = new HashMap<String, String>();
                                    map.put("Latitude", mWarehouseLat);
                                    map.put("Longitude", mWarehouseLong);
                                    map.put("LocationName", mWarehouseAddress);
                                    location.add(map);
                                }
                            }
                            //for warehouse boy latlong
                            JsonObject resShipping = responseObject.get("shipping").getAsJsonObject();
                            if (resDeliveryBoy != null) {
                                mShippingLat = resShipping.get("lat").getAsString();
                                mShippingLong = resShipping.get("long").getAsString();

                                //for warehouse latlong
                                if (!mShippingLat.equals("") && !mShippingLong.equals("")) {
                                    shippingLat = Double.valueOf(mShippingLat);
                                    shippingLong = Double.valueOf(mShippingLong);
                                    //for warehouse
                                    mShippingLatLng = new LatLng(shippingLat, shippingLong);
                                    mShippingAddress = getAddressFromLatLng(getActivity(), shippingLat, shippingLong);

                                    //add location to arrayList for warehouse
                                    map = new HashMap<String, String>();
                                    map.put("Latitude", mShippingLat);
                                    map.put("Longitude", mShippingLong);
                                    map.put("LocationName", mShippingAddress);
                                    location.add(map);
                                }
                            }

                               //for adding marker on map
                            for (int i = 0; i < location.size(); i++) {
                                Latitude = Double.parseDouble(location.get(i).get("Latitude").toString());
                                Longitude = Double.parseDouble(location.get(i).get("Longitude").toString());
                                String name = location.get(i).get("LocationName").toString();
                                MarkerOptions marker = new MarkerOptions().position(new LatLng(Latitude, Longitude)).title(name);
                                //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
                                if (i == 0) {
                                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                    mMap.addMarker(marker);
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude,Longitude), 11));
                                } else if (i == 1) {
                                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                                    mMap.addMarker(marker);
                                } else if (i == 2) {
                                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                    mMap.addMarker(marker);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        CustomProgressDialog.getInstance().dismissDialog();
                        Toast.makeText(getActivity(), "Error loading data", Toast.LENGTH_SHORT).show();
                        Log.e("Error", "onError errorCode : " + error.getErrorCode());
                        Log.e("Error", "onError errorBody : " + error.getErrorBody());
                        Log.e("Error", "onError errorDetail : " + error.getErrorDetail());
                    }
                });
    }
}