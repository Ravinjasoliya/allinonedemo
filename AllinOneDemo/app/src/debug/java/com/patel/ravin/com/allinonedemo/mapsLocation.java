package com.patel.ravin.com.allinonedemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class mapsLocation extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


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
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                        //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();


        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        googleApiClient.connect();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {


        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
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

    public void findAddress(double lat, double longi) {
        Geocoder geocoder = new Geocoder(mapsLocation.this, Locale.getDefault());
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

    @Override
    public void onConnectionSuspended(int i) {

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

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
