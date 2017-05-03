package com.example.guimoye.mario.Servicios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import com.example.guimoye.mario.VerDatosPerfil.VerDatosPerfilClienteLocalizar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyServiceTaxi extends Service {

    public static String TAG="MISERVICIO";
    public static final int NOTIFICACION_ID = 1;
    String correo, tipo;
    Vibrator vibra;
    String IP="http://192.168.1.9:8282/mario/carrerataxi.php?modelo=4&correo=";
    private boolean bandera=false;
    String devuelve;
    String getTipo,correo_cliente;

    public MyServiceTaxi() {
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

       // Log.e(TAG,"SE LLAMO ONSTARTCOMMAND ip:"+IP);
        correo  = intent.getStringExtra("usr");
        IP+=correo;
        tipo    = intent.getStringExtra("tip");
        Log.e(TAG, "EL IP DE BUSQUEDA ES:" + IP);
        Log.e(TAG, "SE LLAMO ONSTARTCOMMAND TAXI eUser :" + correo);
        Log.e(TAG, "SE LLAMO ONSTARTCOMMAND TAXI TIPO:" + tipo);
        new Hilo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"1");
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


    private void crearVentana(){
        vibra.vibrate(5000);
        Intent dialogIntent = new Intent(this, VerDatosPerfilClienteLocalizar.class);
        dialogIntent.putExtra("correo_cliente", correo_cliente);
        dialogIntent.putExtra("correo_taxi", correo);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }

    public class Hilo extends AsyncTask<String,Void,String>{

       // String ss="";
        URL url = null; // Url de donde queremos obtener información

        public Hilo() {
            super();
        }

        @Override
        protected String doInBackground(String... voids) {
            int cn=0;
            if(voids[0]=="1"){

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
                                JSONArray alumnosJSON = respuestaJSON.getJSONArray("carrerastaxi");   // estado es el nombre del campo en el JSON

                                for(int i=0;i<result.length();i++){

//                                    devuelve        =   alumnosJSON.getJSONObject(i).getString("aviso");
                                    correo_cliente  =   alumnosJSON.getJSONObject(i).getString("correo_cliente");
                                    getTipo         =   alumnosJSON.getJSONObject(i).getString("aviso");


                                   if (bandera==false) {

                                        if(getTipo.equals("1")){
                                            crearVentana();
                                            Log.e("del sss es","del sss: "+getTipo);
                                            Thread.sleep(5000);
                                            try {

                                                url = new URL("http://192.168.1.9:8282/mario/carrerataxi.php?modelo=3&correo="+correo+"");
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



                }

                } catch (Exception e) {}
            }


            }
            return devuelve;
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
