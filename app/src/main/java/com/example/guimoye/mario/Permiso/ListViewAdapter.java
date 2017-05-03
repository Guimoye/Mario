package com.example.guimoye.mario.Permiso;

/**
 * Created by Guimoye on 02/12/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.guimoye.mario.R;
import com.example.guimoye.mario.VerDatosPerfil.VerDatosPerfil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> correo;
    ArrayList<String> imagenes;
    ArrayList<Integer> estados;
    String[] getTipo;

    LayoutInflater inflater;

    public ListViewAdapter(Context context,  ArrayList<String> correo, ArrayList<String> imagenes, ArrayList<Integer> estados, String[] getTipo) {
        this.context    =   context;
        this.correo     =   correo;
        this.imagenes   =   imagenes;
        this.estados    =   estados;
        this.getTipo    =   getTipo;
        inflater = LayoutInflater.from(context);
        Log.e("llamooo lenarr","llenarrr "+getTipo[0]);
    }

    public int getCount() {
        return correo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView txtPermiso;
        CircleImageView imgPermiso;
        ToggleButton tbgPermiso;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.modelo_lista_permisos, parent, false);

        txtPermiso = (TextView) itemView.findViewById(R.id.lPermiso);
        imgPermiso = (CircleImageView) itemView.findViewById(R.id.lImagen);
        tbgPermiso = (ToggleButton)itemView.findViewById(R.id.btPermiso);

        txtPermiso.setText(correo.get(position));
        Picasso.with(context).invalidate(imagenes.get(position));
        Picasso.with(context).load(imagenes.get(position)).into(imgPermiso);

        if(estados.get(position)==0){
            tbgPermiso.setChecked(false);
        }else{
            tbgPermiso.setChecked(true);
        }

        tbgPermiso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.e("presiono si","si "+position);
                    cambiarEstados(2,"con",position);
                } else {
                    Log.e("presiono no","no "+position);
                    cambiarEstados(0,"sin",position);
                }
            }
        });

        imgPermiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VerDatosPerfil.class);
                intent.putExtra("correo", correo.get(position));
                intent.putExtra("tipo", getTipo[position]);
                Log.e("llamooo lenarr","llenarrr desde el viewww "+getTipo[position]);
                context.startActivity(intent);
              //  Toast.makeText(context,"presiono imagen nro "+position,Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }


    private void cambiarEstados(int es, final String msg,int i){
        RequestQueue queue;
        queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://192.168.1.9:8282/mario/usuarioGet.php?modelo=7&correo="+correo.get(i)+"&status="+es+"",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(context,"Usuario "+msg+" permiso",Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Esto trajoo ","trajo estooooo: "+error);
            } });

        Log.e("Esto trajoo ","trajo estooooo: "+"http://192.168.1.9:82822/mario/usuarioGet.php?modelo=7&correo"+correo.get(i)+"&status="+es+"");
        queue.add(stringRequest);
    }
}