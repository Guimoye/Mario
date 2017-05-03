package com.example.guimoye.mario.VerDatosPerfil;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.mario.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends ActionBarActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String correo,tlfno;
    double latitud, longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Bundle bolsa = getIntent().getExtras();
        correo = bolsa.getString("correo");
        tlfno  = bolsa.getString("tlfno");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.e("dando estooo ", "lcalizando estoo del correooo: " + correo);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(googleMap.MAP_TYPE_HYBRID);
        UiSettings uiSettings = mMap.getUiSettings();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        uiSettings.setZoomControlsEnabled(true);


        RequestQueue queue;
        queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.9:8282/mario/ubicacions.php?modelo=3&correo="+correo+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject   =   new JSONObject(response);
                            JSONArray urls          =   jsonObject.getJSONArray("Ubicaciones");

                            for(int i=0;i<urls.length();i++){
                                latitud     =   urls.getJSONObject(i).getDouble("latitud");
                                longitud    =   urls.getJSONObject(i).getDouble("longitud");
                            }

                        } catch (JSONException e) { }

                        LatLng sydney = new LatLng(latitud, longitud);
                        mMap.addMarker(new MarkerOptions().position(sydney)
                                .title("Soy: "+correo)
                                .snippet("Mi Nro: "+tlfno)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_useron)));

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,18));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

        queue.add(stringRequest);

    }
}
