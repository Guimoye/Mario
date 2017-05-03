package com.example.guimoye.mario.Ubicacion;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.mario.R;
import com.example.guimoye.mario.VerDatosPerfil.VerDatosPerfil;
import com.example.guimoye.mario.VerDatosPerfil.VerDatosPerfilCliente;
import com.example.guimoye.mario.VerDatosPerfil.VerDatosPerfilTaxi;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Guimoye on 20/11/2016.
 */

public class frag_ubicacion extends Fragment implements OnMapReadyCallback{

    MapView mMapView;
    GoogleMap mGoogleMap;
    String getCorreo;
    AlertDialog ad;
    JSONArray urls;
    View v;

    LayoutInflater inflater2;

    String []Susuario;
    String []Slatitud;
    String []Slongitud;
    String []STipo;
    String []SEstado;
    LatLng []sydney;
    Bundle savedInstanceStatee;

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        savedInstanceStatee =   savedInstanceState;
        inflater2           =   inflater;
        v                   =   inflater.inflate(R.layout.fragment_ubicacion, container, false);
        mMapView            =   (MapView) v.findViewById(R.id.mapView);
        //menu1               =   (FloatingActionButton) v.findViewById(R.id.subFloatingMenu1);


        savedInstanceStatee =   this.getArguments();
        getCorreo           =   savedInstanceStatee.getString("correo");

        mMapView.onCreate(savedInstanceStatee);
        mMapView.getMapAsync(this);


        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

            getUbicaciones();

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

            @Override
            public boolean onMarkerClick(Marker arg0) {
            if(arg0.getSnippet().equals("Taxista")){
                Intent intent = new Intent(getActivity(), VerDatosPerfilTaxi.class);
                intent.putExtra("correo", arg0.getTitle());
                intent.putExtra("correoActi", getCorreo);

                getActivity().startActivity(intent);
              //  Toast.makeText(getActivity(), arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
            }else{
                if(arg0.getSnippet().equals("Cliente")){
                    Intent intent = new Intent(getActivity(), VerDatosPerfilCliente.class);
                    intent.putExtra("correo", arg0.getTitle());
                    intent.putExtra("correoActi", getCorreo);
                    getActivity().startActivity(intent);

                }
            }
                return true;
            }



        });
    }


    private void getUbicaciones(){
        RequestQueue queue;
        queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://192.168.1.9:8282/mario/ubicacions.php?modelo=2",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject respuestaJSON = new JSONObject(response.toString());
                            urls = respuestaJSON.getJSONArray("Ubicaciones");   // estado es el nombre del campo en el JSON
                            reset();


                            for(int i=0;i<response.length();i++){

                                Susuario[i]     =   urls.getJSONObject(i).getString("correo");
                                STipo[i]        =   urls.getJSONObject(i).getString("tipo");
                                Slatitud[i]     =   urls.getJSONObject(i).getString("latitud");
                                Slongitud[i]    =   urls.getJSONObject(i).getString("longitud");
                                SEstado[i]      =   urls.getJSONObject(i).getString("status");
                                sydney[i]       =   new LatLng(Double.parseDouble(Slatitud[i]),
                                                    Double.parseDouble(Slongitud[i]));

                                if(STipo[i].equals("Taxista") && SEstado[i].equals("1")){
                                    mGoogleMap.addMarker(new MarkerOptions().position(sydney[i]).title(Susuario[i]).snippet(STipo[i])
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_taxion)));
                                }else{
                                    if(STipo[i].equals("Taxista") && SEstado[i].equals("2")){
                                        mGoogleMap.addMarker(new MarkerOptions().position(sydney[i]).title(Susuario[i]).snippet(STipo[i])
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_taxioff)));
                                    }
                                }

                                if(STipo[i].equals("Cliente") && SEstado[i].equals("1")){
                                    mGoogleMap.addMarker(new MarkerOptions().position(sydney[i]).title(Susuario[i]).snippet(STipo[i])
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_useron)));
                                }else{
                                    if(STipo[i].equals("Cliente") && SEstado[i].equals("2")){
                                        mGoogleMap.addMarker(new MarkerOptions().position(sydney[i]).title(Susuario[i]).snippet(STipo[i])
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_useroff)));
                                    }
                                }


                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicame(),12));
                            }



                        }catch (Exception e){}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

        queue.add(stringRequest);
    }


    public LatLng ubicame(){
        LatLng ub = null;
        Location location;
        LocationManager locationManager;
            locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
            boolean gpsActivo = locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);

            if (gpsActivo) {
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    return null;
                }
                /*
                locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER
                ,1000 *60 //tiempo
                ,10,this); //cada vez que me mueva en metros*/

                location    = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                ub          = new LatLng(location.getLatitude(),location.getLongitude());
            }
        return ub;
    }

    private void reset(){
        Susuario    =   new String[urls.length()];
        Slatitud    =   new String[urls.length()];
        Slongitud   =   new String[urls.length()];
        STipo       =   new String[urls.length()];
        SEstado     =   new String[urls.length()];
        sydney      =   new LatLng[urls.length()];
    }



    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


}
