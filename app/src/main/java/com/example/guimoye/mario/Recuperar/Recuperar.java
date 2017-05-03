package com.example.guimoye.mario.Recuperar;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.mario.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

public class Recuperar extends ActionBarActivity {

    private String clave=null, pregunta, respuesta,correo;
    private AutoCompleteTextView  txtcorreo, txtpregunta, txtrespuesta;
    private View focusView = null;
    private Button bRecuperar;
    private int estado=0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        txtcorreo = (AutoCompleteTextView) findViewById(R.id.txtCorreoRecuperar);
        txtpregunta = (AutoCompleteTextView) findViewById(R.id.txtPreguntaRecuperar);
        txtrespuesta = (AutoCompleteTextView) findViewById(R.id.txtRespuestaRecuperar);
        bRecuperar = (Button) findViewById(R.id.recuperar_button);

        bRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View  vv= v;
                limpiarRequired();
                    if (txtcorreo.getText().toString().contains("@") && txtcorreo.getText().toString().contains(".com")) {
                        if (txtpregunta.getText().length() != 0) {
                            if (txtrespuesta.getText().length() != 0) {
                                correo      =   txtcorreo.getText().toString();
                                pregunta    =   txtpregunta.getText().toString();
                                respuesta   =   txtrespuesta.getText().toString();
                              /*  Toast.makeText(getApplicationContext(),"Aqui deberias recuperar la clave",Toast.LENGTH_LONG).show();
                                limpiar();*/

                                RequestQueue queue;
                                String url;
                                queue = Volley.newRequestQueue(Recuperar.this);
                                url="http://192.168.1.9:8282/mario/usuarioGet.php?modelo=2&" +
                                        "correo="+correo+"" +
                                        "&pregunta="+pregunta+"" +
                                        "&respuesta="+respuesta+"";
                                // Request a string response from the provided URL.

                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject respuestaJSON = new JSONObject(response.toString());
                                                    clave=respuestaJSON.getJSONObject("estado").getString("clave");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                                if(clave!=null){
                                                    Snackbar.make(vv, "Contraseña recuperada: "+clave, Snackbar.LENGTH_LONG)
                                                            .setAction("Action", null).show();
                                                    clave=null;
                                                    limpiar();
                                                }else{
                                                    if(clave==null){
                                                        Toast.makeText(getApplicationContext(),"Disculpe no se ha podido recuperar " +
                                                                "\nla contraseña revise sus datos",Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(Recuperar.this, "Disculpe no hay conexion", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                // Add the request to the RequestQueue.
                                queue.add(stringRequest);


                            } else {
                                txtrespuesta.setError(getString(R.string.error_field_required));
                                focusView = txtrespuesta;
                                focusView.requestFocus();
                            }
                        } else {
                            txtpregunta.setError(getString(R.string.error_field_required));
                            focusView = txtpregunta;
                            focusView.requestFocus();
                        }
                    } else {
                        txtcorreo.setError(getString(R.string.error_invalid_email));
                        focusView = txtcorreo;
                        focusView.requestFocus();
                    }

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void limpiar() {
        txtcorreo.setText(null);
        txtpregunta.setText(null);
        txtrespuesta.setText(null);
    }

    private void limpiarRequired() {
        txtcorreo.setError(null);
        txtpregunta.setError(null);
        txtrespuesta.setError(null);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Recuperar Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
