package com.project.sam.bitservices;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by samuel.coianiz1 on 26/05/2017.
 */
public class mapActivity extends Activity implements OnMapReadyCallback {

    Bundle bundle;
    String address, suburb;
    double lat, lon;
    LatLng jobCoord;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplayout);
        address = getIntent().getStringExtra("address");
        suburb = getIntent().getStringExtra("suburb");
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Geocoder coder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = coder.getFromLocationName(address, 1);
            if(addresses != null && !addresses.isEmpty()){
                lat = addresses.get(0).getLatitude();
                lon = addresses.get(0).getLongitude();
                jobCoord = new LatLng(lat, lon);
            }
            else {Toast.makeText(this, "We couldn't find this address. Please check with the client to confirm.", Toast.LENGTH_LONG).show();
            return;}
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jobCoord, 13));
        googleMap.addMarker(new MarkerOptions().title("Job Location").snippet(suburb).position(jobCoord).draggable(true));
    }

}
