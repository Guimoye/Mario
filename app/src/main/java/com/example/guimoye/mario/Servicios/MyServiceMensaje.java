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
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.guimoye.mario.Mensajes.MostrarMensaje;
import com.example.guimoye.mario.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyServiceMensaje extends Service {

    public static String TAG="MISERVICIO";
    public static final int NOTIFICACION_ID = 1;

    Vibrator vibra;
    String IP="http://192.168.1.9:8282/mario/mensaje.php?modelo=1&estado=1";
    private boolean bandera=false;
    String devuelve;

    public MyServiceMensaje() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        vibra = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        Log.e(TAG,"SE LLAMO ONCREATEMensaje");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e(TAG,"SE LLAMO ONSTARTCOMMAND ip:"+IP);

        new Hilo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"1");
        return START_STICKY;
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


    private void crearNotificacionBasica(String msj){
        Uri defauldSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Construccion de la accion del intent implicito
      // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/index.html"));
        Intent intent = new Intent(this.getApplicationContext(),MostrarMensaje.class);


    //    Toast.makeText(this.getApplicationContext(),"sapo",Toast.LENGTH_SHORT).show();
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent, 0);

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
        builder.setSubText(msj);

        //Enviar la notificacion
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICACION_ID, builder.build());
    }

    public class Hilo extends AsyncTask<String,Void,String>{

        String ss="";
        URL url = null; // Url de donde queremos obtener información

        public Hilo() {
            super();
        }

        @Override
        protected String doInBackground(String... voids) {
            int cn=0;
            if(voids[0]=="1"){

                while(true){
       // cn++;
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

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder

                        }
                     //   Log.e("esto esssss ", "" + result.toString());
                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        if(result.length()<=18){
                          // Log.e("EMERGENCIAAA", "EMERGENCIAAA 0 "+result.length());
                        }else{
                            if(result.length()>=20){
                                JSONArray alumnosJSON = respuestaJSON.getJSONArray("mensajes");   // estado es el nombre del campo en el JSON
                                for(int i=0;i<result.length();i++){
                                    devuelve= ss = alumnosJSON.getJSONObject(i).getString("mensaje");

                                    if (bandera==false) {

                                        crearNotificacionBasica(ss);
                                        vibra.vibrate(5000);

                                        try {

                                            url = new URL("http://192.168.1.9:8282/mario/mensaje.php?modelo=2&status=0");
                                            HttpURLConnection connectionn = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                                            connectionn.setRequestProperty("User-Agent", "Mozilla/5.0" +
                                                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                                            //connection.setHeader("content-type", "application/json");

                                            // Log.e("holaaa1 ","holaa "+cn);
                                            int respuestaa = connectionn.getResponseCode();
                                            StringBuilder resultt = new StringBuilder();

                                            if (respuestaa == HttpURLConnection.HTTP_OK) {
                                                Log.e("NO PASA NADAAA", "BORRROOOOO");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                        bandera=true;



                                    } else {
                                        if ( bandera==true) {
                                            bandera=false;
                                       //     Log.e("NO PASA NADAAA", "NO PASA NADAAA");
                                        }
                                    }
                                }

                            }

                        }

                }

                } catch (Exception e) {}
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

}
