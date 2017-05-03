package com.example.guimoye.mario.Servicios;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyServiceLocalizame extends Service implements LocationListener {

    public static String TAG = "MISERVICIO";
    RemoteViews contentView;
    String correo, tipo;
    double latitud, longitud;
    int    estado;
    Location location;
    LocationManager locationManager;
    boolean gpsActivo;
    boolean bandera;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "SE LLAMO ONCREATE LOCALIZAMEee");
        bandera= true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        correo  = intent.getStringExtra("usr");
        tipo    = intent.getStringExtra("tip");
        Log.e(TAG, "SE LLAMO ONSTARTCOMMAND LOCALIZAMEEeUser :" + correo);
        Log.e(TAG, "SE LLAMO ONSTARTCOMMAND LOCALIZAMEEeeTipo :" + tipo);
        new Hilo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"1");
      //  getLocation();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "SE DETUVO LOCALIZAME");
        bandera =   false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onLocationChanged(Location location) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }

    public class Hilo extends AsyncTask<String,Void,String> {

        String ss="";

        public Hilo() {
            super();
        }

        @Override
        protected String doInBackground(String... voids) {

            if(voids[0]=="1"){


                        while(bandera){

                            try {
                                locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
                                gpsActivo = locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);

                                if (gpsActivo) {
                                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                                            != PackageManager.PERMISSION_GRANTED
                                            && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                                            != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling

                                        return null;
                                    }
                /*
                locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER
                ,1000 *60 //tiempo
                ,10,this); //cada vez que me mueva en metros*/

                                    location    = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                                    latitud     = location.getLatitude();
                                    longitud    = location.getLongitude();


                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                     String url="http://192.168.11.115:8282/mario/ubicacions.php?modelo=1" +
                                            "&correo="+ correo +"" +
                                            "&tipo="+tipo+"" +
                                            "&latitud="+latitud+"" +
                                            "&longitud="+longitud+"" +
                                             "&estado="+getEstado()+"";
                                    // Request a string response from the provided URL.

                                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
/*
                                                    Log.e("enviando ","enviandooo: http://192.168.11.115:8282/mario/ubicacions.php?modelo=1" +
                                                            "&correo="+ correo +"" +
                                                            "&tipo="+tipo+"" +
                                                            "&latitud="+latitud+"" +
                                                            "&longitud="+longitud+"" +
                                                            "&estado="+getEstado()+"");
*/
                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {}
                                    });
                                    queue.add(stringRequest);
                                }



                                Thread.sleep(30000);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }


            }
            return ss;
        }

        @Override
        protected void onPreExecute() {
            Log.e("va a comenzar el hilo","va a comenzar el hilo");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            // Log.e("termino el hilo","termino el hilo");
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }

    private int getEstado(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url="http://192.168.11.115:8282/mario/usuarioGet.php?modelo=9&correo="+correo+"";
        // Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray urls        = jsonObject.getJSONArray("result");

                            for(int i=0;i<urls.length();i++){
                                estado       =   urls.getJSONObject(i).getInt("status");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        queue.add(stringRequest);

        return estado;
    }


}
