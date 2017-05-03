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

public class MostrarMensajeCliente extends AppCompatActivity {

    TextView txtMensaje;
    Button boton;
    String correo,correo_taxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muestra_mensaje);
        txtMensaje      =   (TextView)findViewById(R.id.txtMensaje);
        boton           =   (Button)findViewById(R.id.buttonAceptarMensaje);
        Bundle bolsa    =   getIntent().getExtras();
        correo_taxi     =   bolsa.getString("correo_taxi");

        txtMensaje.setText("el usuario "+correo_taxi+" a aceptado la carrera en breves minutos estará en su ubicacion");

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
