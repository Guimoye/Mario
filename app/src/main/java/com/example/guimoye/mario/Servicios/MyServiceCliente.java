package com.example.guimoye.mario.Servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.mario.Mensajes.MostrarMensaje;
import com.example.guimoye.mario.Mensajes.MostrarMensajeCliente;
import com.example.guimoye.mario.R;
import com.example.guimoye.mario.VerDatosPerfil.VerDatosPerfilClienteLocalizar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.app.PendingIntent.*;

public class MyServiceCliente extends Service {

    public static String TAG="MISERVICIO";
    String correo, tipo;
    Vibrator vibra;
    String IP="http://192.168.1.9:8282/mario/carreracliente.php?modelo=4&correo=";
    private boolean bandera=false;
    String getTipo, correo_taxi;
    public MyServiceCliente() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        vibra = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        Log.e(TAG,"SE LLAMO ONCREATE CLIENTE");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

       // Log.e(TAG,"SE LLAMO ONSTARTCOMMAND ip:"+IP);
        correo  = intent.getStringExtra("usr");
        IP+=correo;
        tipo    = intent.getStringExtra("tip");
        Log.e(TAG, "EL IP DE BUSQUEDA ES:" + IP);
        Log.e(TAG, "SE LLAMO ONSTARTCOMMAND TAXI eUser :" + correo);
        Log.e(TAG, "SE LLAMO ONSTARTCOMMAND TAXI TIPO:" + tipo);
        //new Hilo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"1");
        new Hilo().execute();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"SE DETUVO ESTOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }




    private void crearNotificacion(){
        vibra.vibrate(5000);

        RequestQueue queue= Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.9:8282/mario/historial.php?modelo=1&" +
                "correo="+correo+"" +
                "&correohis="+correo_taxi+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


        Uri defauldSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Construccion de la accion del intent implicito
        // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/index.html"));
        Intent intent = new Intent(this.getApplicationContext(),MostrarMensajeCliente.class);
        intent.putExtra("correo_taxi", correo_taxi);

        PendingIntent pendingIntent = getActivity(this.getApplicationContext(), 0, intent, 0);


        //Construccion de la notificacion;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_taxi);
        builder.setContentIntent(pendingIntent);
        builder.setSound(defauldSound);
        builder.setAutoCancel(true);
        // builder.setProgress(100,50,false);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_taxi));
        builder.setContentTitle(getResources().getText(R.string.app_name));
        builder.setContentText("¡ Mensaje !");
        builder.setSubText(correo_taxi+" a aceptado la carrera");

        //Enviar la notificacion
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

    }

    public class Hilo extends AsyncTask <Void, Integer, Void>{

        URL url = null;

        public Hilo() {  super(); }

        @Override
        protected void onPreExecute() {
            Log.e("va a comenzar el hilo","va a comenzar el hilo cliente");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.e("va a comenzar el hilo","deberia correr cliente aqui");

            while(true){

                try {
                    url = new URL(IP);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder

                        }

                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena


                        if(result.length()>=15){
                            JSONArray alumnosJSON = respuestaJSON.getJSONArray("carrerascliente");   // estado es el nombre del campo en el JSON

                            for(int i=0;i<result.length();i++){

//                                    devuelve        =   alumnosJSON.getJSONObject(i).getString("aviso");
                                correo_taxi     =   alumnosJSON.getJSONObject(i).getString("correo_taxi");
                                getTipo         =   alumnosJSON.getJSONObject(i).getString("aviso");


                                if (bandera==false) {

                                    if(getTipo.equals("1")){
                                        crearNotificacion();
                                        Log.e("del sss es","VIENE UN TAXIIIIII: "+getTipo);
                                        Thread.sleep(5000);
                                        try {

                                            url = new URL("http://192.168.1.9:8282/mario/carreracliente.php?modelo=3&correo="+correo+"");
                                            HttpURLConnection connectionn = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                                            connectionn.setRequestProperty("User-Agent", "Mozilla/5.0" +
                                                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");

                                            int respuestaa = connectionn.getResponseCode();
                                            StringBuilder resultt = new StringBuilder();

                                            if (respuestaa == HttpURLConnection.HTTP_OK) {
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                    }


                                    bandera=true;


                                } else {
                                    if ( bandera==true) { bandera=false; }
                                }

                            }

                        }


                        return null;
                    }

                } catch (Exception e) {}
            }


        }

    }

}
