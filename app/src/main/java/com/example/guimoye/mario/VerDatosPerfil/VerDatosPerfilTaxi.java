package com.example.guimoye.mario.VerDatosPerfil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.mario.Auto.MostrarAuto;
import com.example.guimoye.mario.Loggin.LoginActivity;
import com.example.guimoye.mario.MainActivity;
import com.example.guimoye.mario.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VerDatosPerfilTaxi extends ActionBarActivity {

    FloatingActionButton menuClienteAddFavorito;
    FloatingActionsMenu bExpandibleCliente;

    FloatingActionButton menuTaxiAddFavorito, menuTaxiPedir,menuTaxiAuto;
    FloatingActionsMenu bExpandibleTaxi;

    ImageView imageView;
    RatingBar rtb;
    View v;
    int cont[];
    double ratings[];
    String getCorreo, getCorreoActi;

    String[] correo,id,tipo,clave,nombre,nacionalidad,nrodocumento,telefono,ocupacion,foto,status,pregunta,respuesta;
    AutoCompleteTextView aucorreo,autipo,auclave,aunombre,aunacionalidad,aunrodocumento,autelefono,auocupacion;
    Bitmap[] bitmaps;
    ToggleButton tb;

    final String JSON_ARRAY="result";
    private JSONArray urls;
    String estado="";
    AlertDialog.Builder builder;
    AlertDialog ad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_datos_taxi);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bolsa        =   getIntent().getExtras();
        getCorreo           =   bolsa.getString("correo");
        getCorreoActi       =   bolsa.getString("correoActi");


        imageView           =   (ImageView)findViewById(R.id.verdatos_imagen);
        rtb                 =   (RatingBar)findViewById(R.id.verdatos_ratingBar);
        tb                  =   (ToggleButton)findViewById(R.id.verdatos_toggleButton);

        aucorreo            =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtCorreo);
        autipo              =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtTipo);
        auclave             =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtClave);
        aunombre            =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtNombre);
        aunacionalidad      =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtNacionalidad);
        aunrodocumento      =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtDocumento);
        autelefono          =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtTlfno);
        auocupacion         =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtOcupacion);

        bExpandibleTaxi     =   (FloatingActionsMenu) findViewById(R.id.bFlotante_perfilTaxi) ;
        menuTaxiAddFavorito =   (FloatingActionButton) findViewById(R.id.SubPerfilTaxiAddFavoritos) ;
        menuTaxiPedir       =   (FloatingActionButton) findViewById(R.id.SubPerfilTaxiPedir) ;
        menuTaxiAuto        =   (FloatingActionButton) findViewById(R.id.SubPerfilTaxiAuto) ;

        menuTaxiAddFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue;
                queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.9:8282/mario/favorito.php?modelo=1" +
                        "&correo="+getCorreoActi+"&correofav="+getCorreo+"",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject   =   new JSONObject(response);
                                    Toast.makeText(getApplicationContext(),
                                            jsonObject.getString("Estado"),Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Esto trajoo ","trajo estooooo: "+error);
                    } });

                queue.add(stringRequest);
            }
        });

        menuTaxiPedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue queue;
                queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        "http://192.168.1.9:8282/mario/carrerataxi.php?modelo=2&correo="+getCorreo+"&correocliente="+getCorreoActi+"",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(),"Su solicitud de carrera ya fue enviada \nal taxista "+getCorreo,Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Esto trajoo ","trajo estooooo: "+error);
                    } });

                queue.add(stringRequest);


            }
        });

        menuTaxiAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nuevaVen = new Intent(VerDatosPerfilTaxi.this, MostrarAuto.class);
                nuevaVen.putExtra("correo", getCorreo);
                startActivity(nuevaVen);

            }
        });

        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    estado="1";
                }else{
                    estado="2";
                }

            }
        });

        getUser();
        bloquearElementos();
    }


    private void getUser(){

        RequestQueue queue;
        queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.9:8282/mario/usuarioGet.php?modelo=6&correo="+getCorreo+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            urls = jsonObject.getJSONArray(JSON_ARRAY);


                            bitmaps         =   new Bitmap[urls.length()];

                            correo          =   new String[urls.length()];
                            id              =   new String[urls.length()];
                            tipo            =   new String[urls.length()];
                            clave           =   new String[urls.length()];
                            nombre          =   new String[urls.length()];
                            nacionalidad    =   new String[urls.length()];
                            nrodocumento    =   new String[urls.length()];
                            telefono        =   new String[urls.length()];
                            ocupacion       =   new String[urls.length()];
                            foto            =   new String[urls.length()];
                            status          =   new String[urls.length()];
                            pregunta        =   new String[urls.length()];
                            respuesta       =   new String[urls.length()];

                            for(int i=0;i<urls.length();i++){
                                //imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
                                correo[i]       =   urls.getJSONObject(i).getString("correo");
                                id[i]           =   urls.getJSONObject(i).getString("id");
                                tipo[i]         =   urls.getJSONObject(i).getString("tipo");
                                clave[i]        =   urls.getJSONObject(i).getString("clave");
                                nombre[i]       =   urls.getJSONObject(i).getString("nombre");
                                nacionalidad[i] =   urls.getJSONObject(i).getString("nacionalidad");
                                nrodocumento[i] =   urls.getJSONObject(i).getString("nrodocumento");
                                telefono[i]     =   urls.getJSONObject(i).getString("telefono");
                                ocupacion[i]    =   urls.getJSONObject(i).getString("ocupacion");
                                foto[i]         =   urls.getJSONObject(i).getString("foto");
                                status[i]       =   urls.getJSONObject(i).getString("status");
                                pregunta[i]     =   urls.getJSONObject(i).getString("pregunta");
                                respuesta[i]    =   urls.getJSONObject(i).getString("respuesta");

                                aucorreo.setText(correo[i]);
                                autipo.setText(tipo[i]);
                                auclave.setText(clave[i]);
                                aunombre.setText(nombre[i]);
                                aunacionalidad.setText(nacionalidad[i]);
                                aunrodocumento.setText(nrodocumento[i]);
                                autelefono.setText(telefono[i]);
                                auocupacion.setText(ocupacion[i]);

                                if(status[i].equals("1")){
                                    tb.setChecked(true);
                                }else{ tb.setChecked(false); }

                                Picasso.with(VerDatosPerfilTaxi.this).invalidate(foto[i]);
                                Picasso.with(VerDatosPerfilTaxi.this).load(foto[i]).into(imageView);

                                getRating();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

        queue.add(stringRequest);
    }

    private void getRating(){

        RequestQueue queue;
        queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://192.168.1.9:8282/mario/rating.php?modelo=1&correo="+getCorreo+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject  =   new JSONObject(response);
                            urls        =   jsonObject.getJSONArray(JSON_ARRAY);
                            cont        =   new int[10];
                            ratings     =   new double[urls.length()];

                            for(int i=0;i<urls.length();i++){
                                ratings[i]       =   urls.getJSONObject(i).getDouble("score");

                                if(ratings[i]==0.5){
                                    cont[0]+=1;
                                }else{
                                    if(ratings[i]==1){
                                        cont[1]+=1;
                                    }else{
                                        if(ratings[i]==1.5){
                                            cont[2]+=1;
                                        }else{
                                            if(ratings[i]==2){
                                                cont[3]+=1;
                                            }else{
                                                if(ratings[i]==2.5){
                                                    cont[4]+=1;
                                                }else{
                                                    if(ratings[i]==3){
                                                        cont[5]+=1;
                                                    }else{
                                                        if(ratings[i]==3.5){
                                                            cont[6]+=1;
                                                        }else{
                                                            if(ratings[i]==4){
                                                                cont[7]+=1;
                                                            }else{
                                                                if(ratings[i]==4.5){
                                                                    cont[8]+=1;
                                                                }else{
                                                                    if(ratings[i]==5){
                                                                        cont[9]+=1;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            int  iPosicion = 0;
                            double iNumeroMayor = cont[0];
                            for (int x=1;x<cont.length;x++){
                                if (cont[x]>iNumeroMayor){
                                    iNumeroMayor = cont[x];
                                    iPosicion = x;
                                }
                            }


                            double aux = 0;
                            for (int i =0; i<iPosicion+1; i++){
                                aux+=0.5;
                                Log.e("pasandoo!","pasandooo! ");
                            }
                            Log.e("totaaaL ","totaaal "+aux);
                            rtb.setRating(Float.parseFloat(aux+""));

                        }catch (Exception e){}


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

        queue.add(stringRequest);

    }

    private void bloquearElementos(){
        aucorreo.setEnabled(false);
        autipo.setEnabled(false);
        auclave.setEnabled(false);
        aunombre.setEnabled(false);
        aunacionalidad.setEnabled(false);
        aunrodocumento.setEnabled(false);
        autelefono.setEnabled(false);
        auocupacion.setEnabled(false);
        tb.setEnabled(false);
        rtb.setNumStars(5);
        rtb.setEnabled(false);

    }



}
