package com.example.guimoye.mario.Auto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.mario.R;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Guimoye on 10/11/2016.
 */

public class MostrarAuto extends AppCompatActivity {

    String getCorreo;
    AutoCompleteTextView aucorreo, aumodelo, auplaca, aucolor;
    Button btn_auto;
    private JSONArray urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muestra_auto);

        btn_auto            =   (Button)findViewById(R.id.btn_auto);
        aucorreo            =   (AutoCompleteTextView)findViewById(R.id.txtCorreoAuto);
        aumodelo            =   (AutoCompleteTextView)findViewById(R.id.txtModeloAuto);
        auplaca             =   (AutoCompleteTextView)findViewById(R.id.txtPlacaAuto);
        aucolor             =   (AutoCompleteTextView)findViewById(R.id.txtColorAuto);
        Bundle bolsa        =   getIntent().getExtras();
        getCorreo           =   bolsa.getString("correo");

        aucorreo.setText(getCorreo);
        llenarCampos();

        btn_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void llenarCampos(){
        RequestQueue queue;
        queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://192.168.1.9:8282/mario/carros.php?modelo=4&correo="+getCorreo+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {

                            jsonObject  =   new JSONObject(response);
                            urls        =   jsonObject.getJSONArray("carros");
                            aumodelo.setText(urls.getJSONObject(0).getString("modelocar"));
                            auplaca.setText(urls.getJSONObject(0).getString("placa"));
                            aucolor.setText(urls.getJSONObject(0).getString("color"));

                        }catch (Exception e){}

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

        queue.add(stringRequest);
    }






}
