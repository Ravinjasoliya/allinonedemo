package com.patel.ravin.com.allinonedemo;
// AIzaSyAwRuTjObw768qYl9lteqaYz9voB-vGzNE 
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class mapsLocation1 extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    TextView txtLat, txtLongi, txtAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_location);

        txtLat = (TextView) findViewById(R.id.lat);
        txtLongi = (TextView) findViewById(R.id.longs);
        txtAddress = (TextView) findViewById(R.id.add);

        googleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(15 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(5 * 1000); // 1 second, in milliseconds

        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
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
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location == null) {
            txtLat.setText("Location is null");
        } else {
            findAddress(location.getLatitude(), location.getLongitude());
            txtLat.setText("Lat. : " + location.getLatitude());
            txtLongi.setText("Longi. : " + location.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
Log.e("cinection.....","failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            txtLat.setText("Location is null");
        } else {
            findAddress(location.getLatitude(), location.getLongitude());
            txtLat.setText("Lat. : " + location.getLatitude());
            txtLongi.setText("Longi. : " + location.getLongitude());
        }
    }

    /*public void findAddress(double lat, double longi) {
        Geocoder gc = new Geocoder(MainActivity.this, Locale.getDefault());

        try {
            List<Address> addresses = gc.getFromLocation(lat, longi, 1);
            StringBuilder sb = new StringBuilder();
            if (addresses.size()>0) {
                Address address = addresses.get(0);
//                for (int i=0; i<address.getMaxAddressLineIndex(); i++) {
                sb.append(address.getAddressLine(0)).append("\n");
                sb.append(address.getLocality()).append("\n");
                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());
//                }
                textView.setText("Your Address : \n" + sb.toString());
            }
        } catch (Exception e) {
            textView.setText("Error is : " + e.toString());
            e.printStackTrace();
        }
    }*/



    public void findAddress(double lat, double longi) {
        Geocoder geocoder = new Geocoder(mapsLocation1.this, Locale.getDefault());
        String add="";

        try {
            List<Address> arrayList = geocoder.getFromLocation(lat, longi, 10);
            Log.e("NoAddress", arrayList.size() + "_test");
            if (arrayList.size() > 0) {
                for(int i=0; i<arrayList.size(); i++) {
                    Address address = arrayList.get(i);
                    add += (i+1) + " Address : ";
                    for(int j=0; j<=address.getMaxAddressLineIndex(); j++) {
                        add += address.getAddressLine(j) + ", ";
                    }
                    add += address.getLocality() + "(" + address.getPostalCode() + "), "
                            + address.getCountryName() + "(" + address.getCountryCode() + ")\n";
                }
//                add += arrayList.get(0).getAddressLine(0);
//                add += ", " +arrayList.get(0).getLocality() + "(" + arrayList.get(0).getPostalCode()
//                        + "), " + arrayList.get(0).getCountryName() + "(" + arrayList.get(0).getCountryCode() + ")";
            }
            txtAddress.setText("Address : \n" + add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
