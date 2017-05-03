package com.example.guimoye.mario.VerDatosPerfil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import com.example.guimoye.mario.R;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VerDatosPerfilClienteLocalizar extends ActionBarActivity {

    FloatingActionsMenu bExpandibleCliente;
    Button btn_localizar_cliente;

    ImageView imageView;
    RatingBar rtb;
    View v;
    int cont[];
    double ratings[];
    String getCorreoCliente,getCorreoTaxi;

    String[] correo,id,tipo,clave,nombre,nacionalidad,nrodocumento,telefono,ocupacion,foto,status,pregunta,respuesta;
    AutoCompleteTextView aucorreo,autipo,auclave,aunombre,aunacionalidad,aunrodocumento,autelefono,auocupacion;
    Bitmap[] bitmaps;
    ToggleButton tb;


    private JSONArray urls;

    RequestQueue queue1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_datos_cliente);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bolsa            =   getIntent().getExtras();
        getCorreoCliente        =   bolsa.getString("correo_cliente");
        getCorreoTaxi           =   bolsa.getString("correo_taxi");

        imageView               =   (ImageView)findViewById(R.id.verdatos_imagen);
        rtb                     =   (RatingBar)findViewById(R.id.verdatos_ratingBar);
        tb                      =   (ToggleButton)findViewById(R.id.verdatos_toggleButton);

        aucorreo                =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtCorreo);
        autipo                  =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtTipo);
        auclave                 =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtClave);
        aunombre                =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtNombre);
        aunacionalidad          =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtNacionalidad);
        aunrodocumento          =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtDocumento);
        autelefono              =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtTlfno);
        auocupacion             =   (AutoCompleteTextView)findViewById(R.id.verdatos_txtOcupacion);

        btn_localizar_cliente   =   (Button)findViewById(R.id.btn_localizar_cliente);

        bExpandibleCliente      =   (FloatingActionsMenu) findViewById(R.id.bFlotante_perfilCliente) ;
        bExpandibleCliente.setVisibility(View.INVISIBLE);

        btn_localizar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                queue1 = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.9:8282/mario/historial.php?modelo=1" +
                        "&correo="+getCorreoTaxi+"" +
                        "&correohis="+ getCorreoCliente +"",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                NotificarleCliente();

                                Intent nuevaVen = new Intent(VerDatosPerfilClienteLocalizar.this,
                                        MapsActivity.class);
                                nuevaVen.putExtra("correo", getCorreoCliente);
                                nuevaVen.putExtra("tlfno", telefono[0]);
                                startActivity(nuevaVen);
                                finish();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Esto trajoo ","trajo estooooo: "+error);
                    } });

                queue1.add(stringRequest);



            }
        });


        getUser();
        bloquearElementos();
    }

    private void NotificarleCliente(){

        RequestQueue queue;
        queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://192.168.1.9:8282/mario/carreracliente.php?modelo=2&correo="+getCorreoCliente+"&correotaxi="+getCorreoTaxi+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

        queue.add(stringRequest);

    }

    private void getUser(){

        RequestQueue queue;
        queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.9:8282/mario/usuarioGet.php?modelo=6&correo="+ getCorreoCliente +"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            urls = jsonObject.getJSONArray("result");


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

                                Picasso.with(VerDatosPerfilClienteLocalizar.this).invalidate(foto[i]);
                                Picasso.with(VerDatosPerfilClienteLocalizar.this).load(foto[i]).into(imageView);

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
                "http://192.168.1.9:8282/mario/rating.php?modelo=1&correo="+ getCorreoCliente +"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject  =   new JSONObject(response);
                            urls        =   jsonObject.getJSONArray("result");
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
