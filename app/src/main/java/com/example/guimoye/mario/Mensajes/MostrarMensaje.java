package com.example.guimoye.mario.Mensajes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.mario.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Guimoye on 10/11/2016.
 */

public class MostrarMensaje extends AppCompatActivity {

    TextView txtMensaje;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muestra_mensaje);
        txtMensaje      =   (TextView)findViewById(R.id.txtMensaje);
        boton           =   (Button)findViewById(R.id.buttonAceptarMensaje);

        RequestQueue queue= Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.9:8282/mario/mensaje.php?modelo=4",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respuestaJSON = new JSONObject(response.toString());
                            txtMensaje.setText(respuestaJSON.getJSONObject("mensajes").getString("mensaje"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
